/**
 * 重置搜索条件，只能重置传入的button的祖先中最近的.searchBar的子.searchContent的子input和子select
 */
var JPlaceHolder_check = 'placeholder' in document.createElement('input');
function myResetSearch(button) {
    var $searchContent = $(button).closest('.searchBar').children('.searchContent');
    $searchContent.children('input').val('');
    $searchContent.children('select').each(function () {
        var option = this.options[0];
        if (option == null) {
            return;
        }
        option.selected = true;
        $(this).change();
    });
    if (!JPlaceHolder_check) {
        // 针对placeholder兼容模式
        var $div = $searchContent.children('div');
        $div.children('input').val('');
        $div.children('span').show();
    }
}
/*
 * placeholder兼容ie9及以下版本
 */
var JPlaceHolder = {
    /*_check: function () {
     return 'placeholder' in document.createElement('input');
     },*/
    init: function () {
        // if (!this._check()) {
        if (!JPlaceHolder_check) {
            this.fix();
        }
    },
    fix: function () {
        jQuery('input[placeholder]').each(function (index, element) {
            var self = $(this), txt = self.attr('placeholder');
            /*if (element.value != "") {
             return
             }*/ // 修改，input有值，但有placeholder属性且非空，仍进行placeholder的模拟
            if (txt == null || txt == '') {
                return;
            }
            self.wrap($('<div></div>').css({
                position: 'relative',
                zoom: '1',
                border: 'none',
                background: 'none',
                padding: 'none',
                margin: 'none',
                display: 'inline-block',
                width: '164px',
                float: 'left'
            }));
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span></span>').text(txt).css({
                position: 'absolute',
                left: pos.left,
                top: pos.top,
                height: h,
                lienHeight: h,
                paddingLeft: paddingleft,
                color: '#aaa',
                lineHeight: h + "px"
            }).appendTo(self.parent());
            // 由于前面修改了在input有值时仍进行placeholder的模拟，此处需隐藏掉摸拟出的placeholder
            if (element.value != '') {
                holder.hide();
            }
            self.focusin(function (e) {
                holder.hide();
            }).focusout(function (e) {
                if (!self.val()) {
                    holder.show();
                }
            });
            holder.click(function (e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
$.fn.extend({
    // 重写被jui扩展过的jquery对象的ajaxUrl方法，以实现刷新tab页后自动处理placeholder兼容问题
    ajaxUrl: function (op) {
        var $this = $(this);
        $this.trigger(DWZ.eventType.pageClear);
        $.ajax({
            type: op.type || 'GET',
            url: op.url,
            data: op.data,
            cache: false,
            success: function (response) {
                var json = DWZ.jsonEval(response);

                if (json[DWZ.keys.statusCode] == DWZ.statusCode.error) {
                    if (json[DWZ.keys.message]) alertMsg.error(json[DWZ.keys.message]);
                } else {
                    $this.html(response).initUI();
                    if ($.isFunction(op.callback)) op.callback(response);
                    JPlaceHolder.init(); //《============= 添加的代码
                }

                if (json[DWZ.keys.statusCode] == DWZ.statusCode.timeout) {
                    if ($.pdialog) $.pdialog.checkTimeout();
                    if (navTab) navTab.checkTimeout();

                    alertMsg.error(json[DWZ.keys.message] || DWZ.msg("sessionTimout"), {
                        okCall: function () {
                            DWZ.loadLogin();
                        }
                    });
                }

            },
            error: DWZ.ajaxError,
            statusCode: {
                503: function (xhr, ajaxOptions, thrownError) {
                    alert(DWZ.msg("statusCode_503") || thrownError);
                }
            }
        });
    },
    // 修复jui的excel导出功能的bug
    dwzExport: function () {
        function _doExport($this) {
            var $p = $this.attr("targetType") == "dialog" ? $.pdialog.getCurrent() : navTab.getCurrentPanel();
            var $form = $("#pagerForm", $p);
            var url = $this.attr("href");

            if ($form.size() == 0) {
                window.location = url;
                return;
            }

            var $iframe = $("#callbackframe");
            if ($iframe.size() == 0) {
                $iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
            }

            var pagerFormUrl = $form[0].action;
            var pagerFormTarget = $form[0].target; //《============= 添加的代码
            $form[0].action = url;
            $form[0].target = "callbackframe";
            $form[0].submit(); //《============= 修改的代码

            $form[0].action = pagerFormUrl;
            $form[0].target = pagerFormTarget; //《============= 添加的代码
        }

        return this.each(function () {
            var $this = $(this);
            $this.click(function (event) {
                var title = $this.attr("title");
                /*if (title) {
                 alertMsg.confirm(title, {
                 okCall: function () {
                 _doExport($this);
                 }
                 });
                 } else {
                 _doExport($this);
                 }*/
                // ============= 修改，始终弹框提示 =============
                if (!title) {
                    title = '是否导出报表？';
                }
                alertMsg.confirm(title, {
                    okCall: function () {
                        _doExport($this);
                    }
                });

                event.preventDefault();
            });
        });
    }
});
// 重写navTab.init方法，以实现打开后台后自动加载首页
var navTabInit = navTab.init;
navTab.init = function (options) {
    navTabInit.call(this, options); // 注意作用域的问题
    navTab.openTab('welcome', 'admin/home/welcome', {title: '首页', fresh: true, data: {}});
};
// 以下为两级渠道选择所使用的公共方法
function operatorIdChange() {
    var operatorId = $('#operatorId', navTab.getCurrentPanel()).val();
    var $operatorId2 = $('.operatorId2', navTab.getCurrentPanel());
    $operatorId2.css('display', 'none');
    // 使用$operatorId2.removeProp('name');在ie9以下会将name赋值为undefined而不是删除，
    // 必须加上$operatorId2.removeAttr('name')才可以，此处直接改为用dom原生方法
    $operatorId2.each(function () {
        this.removeAttribute('name');
    });
    var $operatorId2Select = $('#' + operatorId, navTab.getCurrentPanel());
    if ($operatorId2Select.attr('title') != '0') {
        $operatorId2Select.css('display', 'inline-block');
        $operatorId2Select.prop('name', 'common.operatorId2');
    }
}
// 由于在ajax请求时，若服务器报错会返回一个页面，无法被jui识别，故重写其方法在此情形下弹警告框
// 不是所有ajax请求后都走此方法，但它们也会弹框并显示页面内容，若遇到例外情形再进行补充
// 扩展状态码601，表示服务端（部分）操作成功，且消息弹警告框（info和correct框会自动消失）
var DWZAjaxDone = DWZ.ajaxDone;
DWZ.ajaxDone = function (json) {
    if (json[DWZ.keys.statusCode] == undefined) {
        alertMsg.error('出现故障，请联系管理人员!');
        return;
    }
    if (json[DWZ.keys.statusCode] == 601 && json[DWZ.keys.message] && alertMsg) {
        alertMsg.warn(json[DWZ.keys.message]);
        return;
    }
    DWZAjaxDone.call(this, json);
};
function myNavTabAjaxDone(json) {
    DWZ.ajaxDone(json);
    if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok || json[DWZ.keys.statusCode] == 601) {
        if (json.navTabId) { //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
            navTab.reloadFlag(json.navTabId);
        } else { //重新载入当前navTab页面
            var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
            var args = $pagerForm.size() > 0 ? $pagerForm.serializeArray() : {}
            navTabPageBreak(args, json.rel);
        }

        if ("closeCurrent" == json.callbackType) {
            setTimeout(function () {
                navTab.closeCurrentTab(json.navTabId);
            }, 100);
        } else if ("forward" == json.callbackType) {
            navTab.reload(json.forwardUrl);
        } else if ("forwardConfirm" == json.callbackType) {
            alertMsg.confirm(json.confirmMsg || DWZ.msg("forwardConfirmMsg"), {
                okCall: function () {
                    navTab.reload(json.forwardUrl);
                },
                cancelCall: function () {
                    navTab.closeCurrentTab(json.navTabId);
                }
            });
        } else {
            navTab.getCurrentPanel().find(":input[initValue]").each(function () {
                var initVal = $(this).attr("initValue");
                $(this).val(initVal);
            });
        }
    }
}
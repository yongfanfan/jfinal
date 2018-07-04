package com.haitai.haitaitv.component.util.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {
    
    private Workbook workbook = null;  
    private Sheet sheet; // 表格类实例
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";

    /***
     * <pre>
     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类) 
     *   xls:HSSFWorkbook 
     *   xlsx：XSSFWorkbook 
     * @param filePath 
     * @return 
     * @throws IOException
     * </pre>
     */
//    private Workbook getWorkbook(String filePath) throws IOException {
//        Workbook workbook = null;
//        InputStream is = new FileInputStream(filePath);
//        if (filePath.endsWith(EXTENSION_XLS)) {
//            workbook = new HSSFWorkbook(is);
//        } else if (filePath.endsWith(EXTENSION_XLSX)) {
//            workbook = new XSSFWorkbook(is);
//        }
//        return workbook;
//    }
    /** 
     * 判断文件是否存在
     * @param filePath  文件路径 
     * @return 
     */  
    public boolean fileExist(String filePath){  
         boolean flag = false;  
         File file = new File(filePath);  
         flag = file.exists();  
         return flag;  
    }  
    
    /** 
     * 判断文件的sheet是否存在
     * @param filePath   文件路径 
     * @param sheetName  表格索引名 
     * @return 
     */  
    public boolean sheetExist(String filePath,String sheetName){  
         boolean flag = false;  
         File file = new File(filePath);  
         if(file.exists()){    //文件存在  
            //创建workbook  
             try {  
                workbook = new HSSFWorkbook(new FileInputStream(file));  
                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
                sheet = workbook.getSheet(sheetName);    
                if(sheet!=null) {
                    flag = true;
                }
            } catch (Exception e) {  
                e.printStackTrace();  
            }                 
         }else{    //文件不存在  
             flag = false;  
         }            
         return flag;  
    }
    /** 
     * 创建新Sheet并写入第一行数据
     * @param filePath  excel的路径 
     * @param sheetName 要创建的表格索引 
     * @param titleRow excel的第一行即表格头 
     * @throws IOException 
     * @throws FileNotFoundException 
     */  
    public void createSheet(String filePath,String sheetName,String titleRow[]) throws FileNotFoundException, IOException{ 
        FileOutputStream out = null;         
        File excel = new File(filePath);  // 读取文件
        FileInputStream in = new FileInputStream(excel); // 转换为流
        workbook = new HSSFWorkbook(in); // 加载excel的 工作目录       
                          
        workbook.createSheet(sheetName); // 添加一个新的sheet  
        //添加表头  
        Row row = workbook.getSheet(sheetName).createRow(0);    //创建第一行            
        try {              
            for(int i = 0;i < titleRow.length;i++){  
                Cell cell = row.createCell(i);  
                cell.setCellValue(titleRow[i]);  
            } 
            out = new FileOutputStream(filePath);  
            workbook.write(out);
       }catch (Exception e) {  
           e.printStackTrace();  
       }finally {    
           try {    
               out.close();    
           } catch (IOException e) {    
               e.printStackTrace();  
           }    
       }             
    }
    /** 
     * 创建新excel. 
     * @param filePath  excel的路径 
     * @param sheetName 要创建的表格索引 
     * @param titleRow excel的第一行即表格头 
     * @throws IOException 
     */  
    public void createExcel(String filePath,String sheetName,String titleRow[]) throws IOException{  
        //创建workbook  
        workbook = new HSSFWorkbook();  
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
        workbook.createSheet(sheetName);    
        //新建文件  
        FileOutputStream out = null;  
        try {  
            //添加表头  
            Row row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
            for(int i = 0;i < titleRow.length;i++){  
                Cell cell = row.createCell(i);  
                cell.setCellValue(titleRow[i]);  
            }               
            out = new FileOutputStream(filePath);  
            workbook.write(out);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {    
            try {    
                out.close();    
            } catch (IOException e) {    
                e.printStackTrace();  
            }    
        }    
    }  
   
    /** 
     * 往excel中写入. 
     * @param filePath    文件路径 
     * @param sheetName  表格索引 
     * @throws IOException
     */  
    public void writeToExcel(String filePath,String sheetName, List<?> objectList,String titleRow[]) throws IOException{  
        //创建workbook  
        File file = new File(filePath);  
        workbook = new HSSFWorkbook(new FileInputStream(file));  
        FileOutputStream out = null;  
        Sheet sheet = workbook.getSheet(sheetName);  
        for(Object object : objectList){
         // 获取表格的总行数  
            int rowCount = sheet.getLastRowNum() + 1; // 需要加一  
            try {  
                Row row = sheet.createRow(rowCount);     //最新要添加的一行  
                //通过反射获得object的字段,对应表头插入  
                // 获取该对象的class对象  
                Class<? extends Object> class_ = object.getClass();              
                
                for(int i = 0;i < titleRow.length;i++){    
                    String title = titleRow[i];
                    String UTitle = Character.toUpperCase(title.charAt(0))+ title.substring(1, title.length()); // 使其首字母大写;  
                    String methodName  = "get"+UTitle;  
                    Method method = class_.getDeclaredMethod(methodName); // 设置要执行的方法  
                    Object obj = method.invoke(object);
                    String data = null;
                    if(obj != null){
                        data = method.invoke(object).toString(); // 执行该get方法,即要插入的数据
                    }
                    Cell cell = row.createCell(i);  
                    cell.setCellValue(data);  
                }           
                out = new FileOutputStream(filePath);  
                workbook.write(out);  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {    
                try {    
                    out.close();    
                } catch (IOException e) {    
                    e.printStackTrace();  
                }    
            } 
        }
    }  
    
    /**
     * 
     * @param filePath 路径不含文件名
     * @param filename 文件名
     * @param sheetName 
     * @param title 字段名数组
     * @param titleData 字段名数组
     * @param list 数据内容列表
     * 
     * */
    public static void writeExcel(String filePath, String filename, String sheetName, String[] title, String[] titleData, List<?> list)throws Exception{
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        filePath = filePath + File.separator + filename;
        //filePath = "http://192.168.1.188:8080/download/excel/jmk_recharge_cards.xls";
        //Excel文件易车sheet页的第一行
       
        //Excel文件易车每一列对应的数据
        
            
        ExcelUtil em = new ExcelUtil();
        //判断该名称的文件是否存在  
        //boolean fileFlag = em.fileExist(filePath);        
        em.createExcel(filePath,sheetName,title);
        //判断该名称的Sheet是否存在  
        boolean sheetFlag = em.sheetExist(filePath,sheetName);
        //如果该名称的Sheet不存在，则新建一个新的Sheet
        if(!sheetFlag){
           em.createSheet(filePath,sheetName,title);
        }          
        
        //写入到excel 
        em.writeToExcel(filePath,sheetName,list,titleData);  
    }
    
    public static void main(String[] args) throws Exception {  
        String[] title = {"日期", "城市","新发布车源数"};
        String[] titleDate = {"id", "name","promotionId"};
//        List<TbMarketing> list = new ArrayList<>();
//        TbMarketing user = new TbMarketing();
//        user.setId(1L);
//        list.add(user);
//        user = new TbMarketing();
//        user.setId(3L);
//        user.setPromotionId(4L);
//        list.add(user);
//        writeExcel("E://excel", "abc.xls", "测试", title, titleDate, list);
    }
}

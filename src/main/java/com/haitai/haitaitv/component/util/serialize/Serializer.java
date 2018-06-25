package com.haitai.haitaitv.component.util.serialize;

import java.io.IOException;

public interface Serializer {

    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] bytes) throws IOException;

}

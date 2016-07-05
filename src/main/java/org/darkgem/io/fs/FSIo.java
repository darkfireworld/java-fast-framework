package org.darkgem.io.fs;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.darkgem.annotation.Nullable;
import org.darkgem.io.http.HttpIo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.UUID;

public class FSIo {
    Logger logger = LoggerFactory.getLogger(FSIo.class);
    @Autowired
    HttpIo httpIo;
    String url = null;

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 存储一个二进制数据到FS服务器
     *
     * @param data 存储数据
     * @return 最终的URL，如果上传失败，则返回null
     */
    @Nullable
    public String store(byte[] data) {
        try {
            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("upfile", String.format("%s.jpg", UUID.randomUUID().toString()), RequestBody.create(MediaType.parse("image/jpg"), data))
                    .build();
            Call call = httpIo.post(String.format("%s/api/upload/", url), null, requestBody);
            Response response = call.execute();
            if (response.code() != 200) {
                response.body().close();
                throw new IOException(response.message());
            }
            JSONObject jsonObject = JSON.parseObject(response.body().string());
            //判断是否upload成功
            if ("success".equals(jsonObject.getString("info"))) {
                return jsonObject.getJSONArray("file_info").getJSONObject(0).getString("url");
            } else {
                throw new IOException(jsonObject.getString("detail"));
            }
        } catch (Exception e) {
            logger.error(FSIo.class.getName(), e);
        }

        return null;
    }

}

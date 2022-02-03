package com.abo.service;

import com.abo.util.ConstantPropertiesUtils;
import com.abo.util.ResponseResult;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * @author : Abo
 * @date : 2022/1/23 19:42
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 图片上传
     */
    @Override
    public ResponseResult uploadFileAvatar(MultipartFile file) {
        // 通过工具类获取配置数据
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 完成上传
        try {
            // 获取上传的输入流
            InputStream inputStream = file.getInputStream();
            // 防止恶意程序伪装成图片
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null || image.getWidth() == 0 || image.getHeight() == 0) {
                return ResponseResult.error().message("请上传符合规范的图片");
            }

            // 获取文件名称
            String filename = file.getOriginalFilename();

            // 为文件设定唯一名称
           /* 数字类型
            CRC32 crc32 = new CRC32();
            crc32.update(UUID.randomUUID().toString().getBytes());*/
            filename = UUID.randomUUID().toString().replaceAll("-", "") + filename;
            // 按照年月日创建上传的目录存放图片
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + "/" + filename;

            /*
             * 调用oss实现上传
             * 第一个参数  bucket名称
             * 第二个参数  文件名称
             * 第三个参数  输入流
             */
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, filename, inputStream);
            ossClient.shutdown();
            String url = "https://" + bucketName + "." + endPoint + "/" + filename;
            return ResponseResult.success().data("url", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

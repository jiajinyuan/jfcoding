package com.jf.bigdata.hdfs.ha;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * HDFS 开启HA后上次文件
 *
 * @author Junfeng
 */
public class HdfsHaUploadFile {

    /**
     * LOG
     */
    static Logger LOG = LoggerFactory.getLogger(HdfsHaUploadFile.class);

    public static void main(String[] arg) throws IOException {
        //deleteFile("/test/upload");
        //uploadFile("C:\\Users\\jiaji\\Desktop\\test.txt", "/test/upload/test.txt");
        // HA 切合后在上传
        //uploadFile("C:\\Users\\jiaji\\Desktop\\test.txt", "/test/upload/test1.txt");
        uploadFile("C:\\Users\\jiaji\\Desktop\\test.txt", "/test/upload/test2.txt");

    }

    private static Configuration getConf() {
        Configuration conf = new Configuration();

        //设置配置相关的信息，分别对应hdfs-site.xml core-site.xml
        conf.set("fs.defaultFS", "hdfs://jfcluster");
        conf.set("dfs.nameservices", "jfcluster");
        conf.set("dfs.ha.namenodes.jfcluster", "nn1,nn2");
        conf.set("dfs.namenode.rpc-address.jfcluster.nn1", "jf10.com:8020");
        conf.set("dfs.namenode.rpc-address.jfcluster.nn2", "jf11.com:8020");
        conf.set("dfs.client.failover.proxy.provider.jfcluster", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

        //设置实现类，因为会出现类覆盖的问题
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        return conf;
    }

    private static void uploadFile(String localPath, String destPath) {
        LOG.info(" upload file: {} to {}", localPath, destPath);
        Configuration conf = getConf();
        try {
            FileSystem fs = FileSystem.get(URI.create(destPath), conf, "hdfs");
            //fs.copyFromLocalFile(new Path(localPath), new Path(destPath));
            fs.close();
        } catch (IOException e) {
            LOG.error("", e);
        } catch (InterruptedException e) {
            LOG.error("", e);
        }
    }

    private static void deleteFile(String hdfsPath){
        Configuration conf = getConf();
        try {
            FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf, "hdfs");
            fs.deleteOnExit(new Path(hdfsPath));
        } catch (IOException e) {
            LOG.error("", e);
        } catch (InterruptedException e) {
            LOG.error("", e);
        }
    }


}

package com.xcase.aws;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.aws.impl.simple.core.AWSConfigurationManager;

public class AWSApplication {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            String accessKey = AWSConfigurationManager.getConfigurationManager().getLocalConfig().getProperty("aws.aws_access_key_id");
            LOGGER.debug("accessKey is " + accessKey);
            String secretKey = AWSConfigurationManager.getConfigurationManager().getLocalConfig().getProperty("aws.aws_secret_access_key");
            LOGGER.debug("secretKey is " + secretKey);
            AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
            S3ClientBuilder s3ClientBuilder = S3Client.builder().region(Region.US_WEST_2).credentialsProvider(awsCredentialsProvider);
            S3Client s3Client = s3ClientBuilder.build();
            LOGGER.debug("created s3Client");
            ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
            LOGGER.debug("got listBucketsResponse " + listBucketsResponse.toString());
            List<Bucket> bucketList = listBucketsResponse.buckets();
            if (bucketList != null) {
                LOGGER.debug("bucketList size is " + bucketList.size());
            } else {
                LOGGER.debug("bucketList is null");
            }
        } catch (Exception e) {
            LOGGER.warn("exception getting bucket list: " + e.getMessage());
        }

    }

}

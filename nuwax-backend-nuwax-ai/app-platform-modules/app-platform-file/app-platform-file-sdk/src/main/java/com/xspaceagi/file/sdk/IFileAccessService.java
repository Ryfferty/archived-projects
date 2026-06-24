package com.xspaceagi.file.sdk;

public interface IFileAccessService {

    String getFileUrlWithAk(String fileUrl);

    String getFileUrlWithAk(String fileUrl, boolean returnOriginalUrl);

    void checkFileUrlAk(String uri, String ak);

    void checkFileUrlAk0(String uri, String ak);
}

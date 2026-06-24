package com.xspaceagi.agent.core.spec.utils;

import com.alibaba.fastjson2.JSONObject;
import com.xspaceagi.file.sdk.IFileAccessService;
import com.xspaceagi.system.spec.utils.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class UrlFile {

    private static Tika tika = new Tika();

    private static IFileAccessService iFileAccessService;

    private static HttpClient httpClient;

    @Autowired
    public void setIFileAccessService(IFileAccessService iFileAccessService) {
        UrlFile.iFileAccessService = iFileAccessService;
    }

    @Autowired
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public static String parseToString(String url) {
        try {
            url = iFileAccessService.getFileUrlWithAk(url, true);
            return tika.parseToString(new URL(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param url
     * @param charset
     * @return
     */
    public static String urlToText(String url, String charset) {
        byte[] bytes = downLoad(url);
        return new String(bytes, Charset.forName(charset));
    }

    public static String excelToJson(String url) {
        try {
            return JSONObject.toJSONString(ExcelParser.convertExcelToJson(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Map<String, Object>> excelToArray(String url) {
        try {
            return ExcelParser.convertExcelToJson(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String wordToMarkdown(String url) {
        //增加对doc的支持
        url = iFileAccessService.getFileUrlWithAk(url, true);
        if (url.endsWith(".doc")) {
            // 处理doc文件
            return parseToString(url);
        }

        XWPFDocument document;
        try {
            document = new XWPFDocument(getConnectionStream(url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        StringBuilder markdown = new StringBuilder();

        // 遍历文档的元素：段落和表格
        for (IBodyElement element : document.getBodyElements()) {
            if (element instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                String text = paragraph.getText();
                var style = paragraph.getStyle();
                // 获取标题
                if (Objects.nonNull(style) && (style.startsWith("Title"))) {
                    int headingLevel = Integer.parseInt(style.substring("Title".length()).trim());
                    markdown.append(createHeadingLevel(headingLevel, text));
                } else if (Objects.nonNull(style) && style.startsWith("Heading")) {
                    int headingLevel = Integer.parseInt(style.substring("Heading".length()).trim());
                    markdown.append(createHeadingLevel(headingLevel, text));
                } else {
                    // 普通文本段落
                    markdown.append(text).append("\n\n");
                }
            } else if (element instanceof XWPFTable table) {
                // 处理表格
                markdown.append(convertTableToMarkdown(table)).append("\n\n");
            }
        }

        // 关闭文档
        try {
            document.close();
        } catch (IOException e) {
            //
        }
        return markdown.toString();
    }

    // 将 Word 表格转换为 Markdown 表格格式
    private static String convertTableToMarkdown(XWPFTable table) {
        StringBuilder markdown = new StringBuilder();

        int index = 0;
        for (XWPFTableRow row : table.getRows()) {
            if (index == 1) {
                // 添加表格分隔线（对第一行）
                if (!table.getRows().isEmpty()) {
                    for (XWPFTableCell ignored : row.getTableCells()) {
                        markdown.append("|---");
                    }
                    markdown.append("|\n");
                }
            }
            // 处理每一行的单元格
            for (XWPFTableCell cell : row.getTableCells()) {
                markdown.append("| ").append(cell.getText()).append(" ");
            }
            markdown.append("|\n");
            index++;
        }

        return markdown.toString();
    }

    private static String createHeadingLevel(int headingLevel, String text) {
        String h = "";
        for (int i = 0; i < headingLevel; i++) {
            h += "#";
        }
        return h + " " + text + "\n\n";
    }

    public static byte[] urlToBytes(String url) {
        return downLoad(url);
    }

    public static byte[] downLoad(String url) {
        url = iFileAccessService.getFileUrlWithAk(url, true);
        try {
            return httpClient.download(url);
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
        BufferedInputStream bis = null;
        try {
            InputStream is = getConnectionStream(url);
            bis = new BufferedInputStream(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int i;
            byte[] buf = new byte[1024];
            while ((i = bis.read(buf)) != -1) {
                bos.write(buf, 0, i);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error("关闭BufferedInputStream失败", e);
                }
            }
        }
    }

    private static InputStream getConnectionStream(String httpUrl) throws Exception {
        URI uri = new URI(httpUrl);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.connect();
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            return httpClient.downloadStream(httpUrl);
        }
    }
}

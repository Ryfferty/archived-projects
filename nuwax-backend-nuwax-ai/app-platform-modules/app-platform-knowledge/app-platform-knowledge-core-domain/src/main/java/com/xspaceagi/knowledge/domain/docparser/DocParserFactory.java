package com.xspaceagi.knowledge.domain.docparser;

import com.xspaceagi.knowledge.domain.docparser.parse.DocParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocParserFactory {

    private final List<DocParser> parsers;

    public DocParserFactory(List<DocParser> parsers) {
        this.parsers = parsers;
    }

    public DocParser getParser(Integer dataType, String docUrl) {
        return parsers.stream()
                .filter(parser -> parser.isSupport(dataType, docUrl))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported document type"));
    }


}

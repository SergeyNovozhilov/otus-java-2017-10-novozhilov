package ru.otus.GCLogAnalyzer;

import lombok.Data;

@Data
public class JvmHeapSize {
    private Total total;
    private MetaSpace metaSpace;
    private OldGen oldGen;
    private YoungGen youngGen;
}

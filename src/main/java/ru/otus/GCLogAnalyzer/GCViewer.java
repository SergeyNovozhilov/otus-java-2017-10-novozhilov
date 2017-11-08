package ru.otus.GCLogAnalyzer;

import lombok.Data;

@Data
public class GCViewer {
    private String commandLineFlags;

    private GcDurationSummary gcDurationSummary;

    private String responseId;

    private String throughputPercentage;

    private GcStatistics gcStatistics;

    private GcCause[] gcCauses;

    private String graphURL;

    private String isProblem;

    private JvmHeapSize jvmHeapSize;
}

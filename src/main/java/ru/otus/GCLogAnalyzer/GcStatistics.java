package ru.otus.GCLogAnalyzer;

import lombok.Data;

@Data
public class GcStatistics {
    private String fullGCIntervalAvgTime;

    private String fullGCMaxTime;

    private String minorGCTotalTime;

    private String minorGCMinTIme;

    private String totalCreatedBytes;

    private String fullGCAvgTime;

    private String fullGCAvgTimeStdDeviation;

    private String fullGCMinTIme;

    private String fullGCTotalTime;

    private String minorGCAvgTimeStdDeviation;

    private String avgAllocationRate;

    private String measurementDuration;

    private String minorGCMaxTime;

    private String avgPromotionRate;

    private String minorGCIntervalAvgTime;

    private String minorGCAvgTime;

    private String minorGCCount;

    private String fullGCCount;
}

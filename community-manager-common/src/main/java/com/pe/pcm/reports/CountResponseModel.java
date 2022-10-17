package com.pe.pcm.reports;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
public class CountResponseModel implements Serializable {
    private int thisHourCount;
    private int thisDayCount;
    private int thisWeekCount;
    private int thisMonthCount;

    public int getThisHourCount() {
        return thisHourCount;
    }

    public CountResponseModel setThisHourCount(int thisHourCount) {
        this.thisHourCount = thisHourCount;
        return this;
    }

    public int getThisDayCount() {
        return thisDayCount;
    }

    public CountResponseModel setThisDayCount(int thisDayCount) {
        this.thisDayCount = thisDayCount;
        return this;
    }

    public int getThisWeekCount() {
        return thisWeekCount;
    }

    public CountResponseModel setThisWeekCount(int thisWeekCount) {
        this.thisWeekCount = thisWeekCount;
        return this;
    }

    public int getThisMonthCount() {
        return thisMonthCount;
    }

    public CountResponseModel setThisMonthCount(int thisMonthCount) {
        this.thisMonthCount = thisMonthCount;
        return this;
    }
}

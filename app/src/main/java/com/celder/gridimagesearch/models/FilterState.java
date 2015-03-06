package com.celder.gridimagesearch.models;

import java.io.Serializable;

/**
 * Created by celder on 3/6/15.
 */
public class FilterState implements Serializable {
    private static final long serialVersionUID = -39159291382499L;

    public String imageSize;
    public String colorFilter;
    public String imageType;
    public String siteFilter;

    public FilterState(String imageSize, String colorFilter, String imageType, String siteFilter) {
        this.imageSize = imageSize;
        this.colorFilter = colorFilter;
        this.imageType = imageType;
        this.siteFilter = siteFilter;
    }
}

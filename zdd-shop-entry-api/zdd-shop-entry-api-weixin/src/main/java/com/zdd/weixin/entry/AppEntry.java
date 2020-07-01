package com.zdd.weixin.entry;

import lombok.Data;

/**
 * @author Xin
 * @date 2020/7/1 11:49 上午
 * @Content:
 */
@Data
public class AppEntry {

    private String appId;
    private String appName;

    public AppEntry(String appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }
}

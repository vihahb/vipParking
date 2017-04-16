package com.xtel.vparking.vip.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by Vulcl on 10-Feb-17
 */

public class RESP_Image extends RESP_Basic {
    @Expose
    private String name;
    @Expose
    private String server_path;
    @Expose
    private String uri;
    private String file_path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer_path() {
        return server_path;
    }

    public void setServer_path(String server_path) {
        this.server_path = server_path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
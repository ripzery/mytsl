package com.socket9.tsl.Models;

/**
 * Created by visit on 10/4/15 AD.
 */
public class Photo extends BaseModel{

    /**
     * pathUse : http://tsl.socket9.com/uploads/images.png
     * pathSave : uploads/images.png
     */
    private String pathUse;
    private String pathSave;

    public void setPathUse(String pathUse) {
        this.pathUse = pathUse;
    }

    public void setPathSave(String pathSave) {
        this.pathSave = pathSave;
    }

    public String getPathUse() {
        return pathUse;
    }

    public String getPathSave() {
        return pathSave;
    }
}

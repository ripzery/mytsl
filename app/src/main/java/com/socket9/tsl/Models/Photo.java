package com.socket9.tsl.Models;

/**
 * Created by visit on 10/4/15 AD.
 */
public class Photo extends BaseModel{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
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
}

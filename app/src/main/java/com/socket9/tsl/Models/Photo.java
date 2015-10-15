package com.socket9.tsl.Models;

/**
 * Created by Euro on 10/4/15 AD.
 */
public class Photo extends BaseModel {
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * pathUse : http://tsl.socket9.com/uploads/images.png
         * pathSave : uploads/images.png
         */
        private String pathUse;
        private String pathSave;

        public String getPathUse() {
            return pathUse;
        }

        public void setPathUse(String pathUse) {
            this.pathUse = pathUse;
        }

        public String getPathSave() {
            return pathSave;
        }

        public void setPathSave(String pathSave) {
            this.pathSave = pathSave;
        }
    }
}

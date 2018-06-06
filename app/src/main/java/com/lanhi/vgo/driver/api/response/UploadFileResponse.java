package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class UploadFileResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 2018-05-10 10:13:31
         * id : 4
         * imgF : .jpg
         * imgName : d7f89949-5c77-481f-bfb7-74cde19156a1.jpg
         * imgUrl : uploads/d7f89949-5c77-481f-bfb7-74cde19156a1.jpg
         * imgUuid : d7f89949-5c77-481f-bfb7-74cde19156a1.jpg
         * sid : 13
         */

        private String createtime;
        private int id;
        private String imgF;
        private String imgName;
        private String imgUrl;
        private String imgUuid;
        private int sid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgF() {
            return imgF;
        }

        public void setImgF(String imgF) {
            this.imgF = imgF;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUuid() {
            return imgUuid;
        }

        public void setImgUuid(String imgUuid) {
            this.imgUuid = imgUuid;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }
    }
}

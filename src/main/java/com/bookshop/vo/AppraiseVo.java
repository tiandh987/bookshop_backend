package com.bookshop.vo;

/**
 * @Auther: lenovo
 * @Date: 2019/5/14 11:31
 * @Description:
 */
public class AppraiseVo {

    private Integer productId;

    private String text;

    private Integer grade;

    private String createTime;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

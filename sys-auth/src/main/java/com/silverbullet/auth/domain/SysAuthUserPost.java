package com.silverbullet.auth.domain;

public class SysAuthUserPost {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_auth_userpost.ID
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_auth_userpost.USERID
     *
     * @mbggenerated
     */
    private String userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_auth_userpost.POSTID
     *
     * @mbggenerated
     */
    private String postid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_auth_userpost.ID
     *
     * @return the value of sys_auth_userpost.ID
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_auth_userpost.ID
     *
     * @param id the value for sys_auth_userpost.ID
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_auth_userpost.USERID
     *
     * @return the value of sys_auth_userpost.USERID
     *
     * @mbggenerated
     */
    public String getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_auth_userpost.USERID
     *
     * @param userid the value for sys_auth_userpost.USERID
     *
     * @mbggenerated
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_auth_userpost.POSTID
     *
     * @return the value of sys_auth_userpost.POSTID
     *
     * @mbggenerated
     */
    public String getPostid() {
        return postid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_auth_userpost.POSTID
     *
     * @param postid the value for sys_auth_userpost.POSTID
     *
     * @mbggenerated
     */
    public void setPostid(String postid) {
        this.postid = postid == null ? null : postid.trim();
    }
}
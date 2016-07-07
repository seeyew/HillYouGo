package com.seeyewmo.hillyougo.model;

public class Github {
    private String login;
    private String blog;
    private int public_repos;

    public String getLogin() {
        return login;
    }

    public String getBlog() {
        return blog;
    }

    public int getPublicRepos() {
        return public_repos;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setPublicRepos(int publicRepos) {
        this.public_repos = publicRepos;
    }
}

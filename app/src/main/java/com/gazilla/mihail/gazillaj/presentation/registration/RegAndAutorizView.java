package com.gazilla.mihail.gazillaj.presentation.registration;

public interface RegAndAutorizView {


    void registrationRegActivity(String name, String phone, String email, String refererLink, boolean save, String promo);
    void selectEmptyText(String pole);
    void showErrorr(String error);
    void visibleETCode();
    void startProgramm(Boolean response);
}

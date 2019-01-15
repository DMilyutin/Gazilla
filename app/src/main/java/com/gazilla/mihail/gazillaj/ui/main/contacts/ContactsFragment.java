package com.gazilla.mihail.gazillaj.ui.main.contacts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gazilla.mihail.gazillaj.R;

import java.util.List;

public class ContactsFragment extends Fragment {

    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";
    private static final String FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana";

    ConstraintLayout clOpenVK;
    ConstraintLayout clOpenFB;
    ConstraintLayout clOpenInsta;
    ConstraintLayout clOpenMap;
    ConstraintLayout clOpenWWW;
    ConstraintLayout clCallMe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, null);

        clOpenVK = view.findViewById(R.id.clOpenVK);
        clOpenFB = view.findViewById(R.id.clOpenFB);
        clOpenInsta = view.findViewById(R.id.clOpenInsta);
        clOpenMap = view.findViewById(R.id.clOpenMap);
        clOpenWWW = view.findViewById(R.id.clOpenWWW);
        clCallMe = view.findViewById(R.id.clCallMe);

        clOpenVK.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/gazilla_gz"));
            startActivity(intent);
        });

        clOpenFB.setOnClickListener(v -> {
            openLink(getActivity(), "https://www.facebook.com/gazillalounge");
        });

        clOpenInsta.setOnClickListener(v -> {
            openLink(getActivity(), "https://www.instagram.com/gazilla_gz");
        });

        clOpenMap.setOnClickListener(v -> {
            String adr = "Нижний Сусальный переулок 5с1";
            String uri1 = "geo:0,0?q=" + adr  ;
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri1));
            startActivity(mapIntent);
        });

        clOpenWWW.setOnClickListener(v -> {
            openLink(getActivity(), "https://gazilla-lounge.ru");
        });

        clCallMe.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:89652662222"));
            startActivity(intent);
        });

        //openLink(getActivity(), "https://www.instagram.com/gazilla_gz");

        return view;

        //vk - openLink(this, "https://vk.com/gazilla_gz");
        // insta - https://www.instagram.com/gazilla_gz/
        // address -  Нижний Сусальный переулок 5с1, 3 этаж
        // сайт https://gazilla-lounge.ru
        // телефон +7 965 266-22-22
        //

    }

    public static void openLink(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent, 0);

        if (resInfo.isEmpty()) return;

        for (ResolveInfo info: resInfo) {
            if (info.activityInfo == null) continue;
            if (VK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                    || FACEBOOK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                    ) {
                intent.setPackage(info.activityInfo.packageName);
                break;
            }
        }
        activity.startActivity(intent);
    }
}

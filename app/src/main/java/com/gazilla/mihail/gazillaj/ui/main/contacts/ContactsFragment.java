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
/** фрагмент с контактами */
public class ContactsFragment extends Fragment {

    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";
    private static final String FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, null);

        /** Поля с соответсвующими ссылками */
        ConstraintLayout clOpenVK = view.findViewById(R.id.clOpenVK);
        ConstraintLayout clOpenFB = view.findViewById(R.id.clOpenFB);
        ConstraintLayout clOpenInsta = view.findViewById(R.id.clOpenInsta);
        ConstraintLayout clOpenMap = view.findViewById(R.id.clOpenMap);
        ConstraintLayout clOpenWWW = view.findViewById(R.id.clOpenWWW);
        ConstraintLayout clCallMe = view.findViewById(R.id.clCallMe);

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

        return view;

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

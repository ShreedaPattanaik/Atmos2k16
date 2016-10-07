package Helper;

import android.content.Context;
import android.content.Intent;

import com.dota.atmos.atmos2k16.Set.EventSet;

/**
 * Created by admin on 07-10-2016.
 */
public class Share {
    public static void shareData(EventSet eventSet, Context context) {
        //todo get text
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, eventSet.getFbUrl());
        context.startActivity(Intent.createChooser(intent, "Share Using..."));

    }
}

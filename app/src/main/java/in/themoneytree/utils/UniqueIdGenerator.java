package in.themoneytree.utils;

import android.content.Context;
import in.themoneytree.data.local.PrefManager;

/**
 * Created By  Archit
 * on 11/29/2019
 * for TheMoneyTree
 */
public class UniqueIdGenerator {

    // Unique Id is Generated Using Shared Preferences

    public static Integer getId(Context context){
        Integer id =0;
        id = Integer.parseInt(PrefManager.getInstance(context).getIdentity());
        if(id ==-1){
            id =1;
        }else{
            id++;
        }
        PrefManager.getInstance(context).setIdentity(id+"");
        return id;
    }
}

package lector.gi.unibague.gilectorcodigodebarras;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Juan David on 9/05/2018.
 */

public class MainAdaptadorPaginador extends FragmentPagerAdapter {


    public MainAdaptadorPaginador(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentCompras();
            case 1:
                return  new FragmentProducos();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Compras";
            case 1:
                return "Productos";
            default:
                    return "Sin nombre";
        }
    }

}

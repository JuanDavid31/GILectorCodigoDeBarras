package viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class FabricaViewModel {



    public static class FabricaProductosViewModel implements ViewModelProvider.Factory{

        private Context context;

        public FabricaProductosViewModel(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ProductosViewModel.class)) {
                return (T) new ProductosViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public static class FabricaComprasViewModel implements ViewModelProvider.Factory{

        private Context context;

        public FabricaComprasViewModel(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ComprasViewModel.class)) {
                return (T) new ComprasViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public static class FabricaEscaneoViewModel implements ViewModelProvider.Factory{

        private Context context;

        public FabricaEscaneoViewModel(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(EscaneoViewModel.class)) {
                return (T) new EscaneoViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public static class FabricaAdicionProductoViewModel implements ViewModelProvider.Factory{

        private Context context;

        public FabricaAdicionProductoViewModel(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AdicionProductoViewModel.class)) {
                return (T) new AdicionProductoViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    public static class FabricaCompraViewModel implements ViewModelProvider.Factory{

        private Context context;

        public FabricaCompraViewModel(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(CompraViewModel.class)) {
                return (T) new CompraViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}

package com.pubmanager.pubmanager;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.pubmanager.pubmanager.databinding.ActivityHomeBindingImpl;
import com.pubmanager.pubmanager.databinding.ActivityListExpensesBindingImpl;
import com.pubmanager.pubmanager.databinding.ActivityMainBindingImpl;
import com.pubmanager.pubmanager.databinding.ActivitySignUpBindingImpl;
import com.pubmanager.pubmanager.databinding.ActivityUserCategoryBindingImpl;
import com.pubmanager.pubmanager.databinding.ActivityUserTransactionBindingImpl;
import com.pubmanager.pubmanager.databinding.CategoriesCardItemBindingImpl;
import com.pubmanager.pubmanager.databinding.RecyclerviewAdapterLayoutBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYHOME = 1;

  private static final int LAYOUT_ACTIVITYLISTEXPENSES = 2;

  private static final int LAYOUT_ACTIVITYMAIN = 3;

  private static final int LAYOUT_ACTIVITYSIGNUP = 4;

  private static final int LAYOUT_ACTIVITYUSERCATEGORY = 5;

  private static final int LAYOUT_ACTIVITYUSERTRANSACTION = 6;

  private static final int LAYOUT_CATEGORIESCARDITEM = 7;

  private static final int LAYOUT_RECYCLERVIEWADAPTERLAYOUT = 8;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(8);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.activity_home, LAYOUT_ACTIVITYHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.activity_list_expenses, LAYOUT_ACTIVITYLISTEXPENSES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.activity_sign_up, LAYOUT_ACTIVITYSIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.activity_user_category, LAYOUT_ACTIVITYUSERCATEGORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.activity_user_transaction, LAYOUT_ACTIVITYUSERTRANSACTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.categories_card_item, LAYOUT_CATEGORIESCARDITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pubmanager.pubmanager.R.layout.recyclerview_adapter_layout, LAYOUT_RECYCLERVIEWADAPTERLAYOUT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYHOME: {
          if ("layout/activity_home_0".equals(tag)) {
            return new ActivityHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_home is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLISTEXPENSES: {
          if ("layout/activity_list_expenses_0".equals(tag)) {
            return new ActivityListExpensesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_list_expenses is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSIGNUP: {
          if ("layout/activity_sign_up_0".equals(tag)) {
            return new ActivitySignUpBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_sign_up is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYUSERCATEGORY: {
          if ("layout/activity_user_category_0".equals(tag)) {
            return new ActivityUserCategoryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_user_category is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYUSERTRANSACTION: {
          if ("layout/activity_user_transaction_0".equals(tag)) {
            return new ActivityUserTransactionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_user_transaction is invalid. Received: " + tag);
        }
        case  LAYOUT_CATEGORIESCARDITEM: {
          if ("layout/categories_card_item_0".equals(tag)) {
            return new CategoriesCardItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for categories_card_item is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERVIEWADAPTERLAYOUT: {
          if ("layout/recyclerview_adapter_layout_0".equals(tag)) {
            return new RecyclerviewAdapterLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recyclerview_adapter_layout is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(8);

    static {
      sKeys.put("layout/activity_home_0", com.pubmanager.pubmanager.R.layout.activity_home);
      sKeys.put("layout/activity_list_expenses_0", com.pubmanager.pubmanager.R.layout.activity_list_expenses);
      sKeys.put("layout/activity_main_0", com.pubmanager.pubmanager.R.layout.activity_main);
      sKeys.put("layout/activity_sign_up_0", com.pubmanager.pubmanager.R.layout.activity_sign_up);
      sKeys.put("layout/activity_user_category_0", com.pubmanager.pubmanager.R.layout.activity_user_category);
      sKeys.put("layout/activity_user_transaction_0", com.pubmanager.pubmanager.R.layout.activity_user_transaction);
      sKeys.put("layout/categories_card_item_0", com.pubmanager.pubmanager.R.layout.categories_card_item);
      sKeys.put("layout/recyclerview_adapter_layout_0", com.pubmanager.pubmanager.R.layout.recyclerview_adapter_layout);
    }
  }
}

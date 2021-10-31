// Generated by data binding compiler. Do not edit!
package com.pubmanager.pubmanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.pubmanager.pubmanager.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityListExpensesBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView recyclerview;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textviewBack;

  protected ActivityListExpensesBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RecyclerView recyclerview, TextView textView2, TextView textviewBack) {
    super(_bindingComponent, _root, _localFieldCount);
    this.recyclerview = recyclerview;
    this.textView2 = textView2;
    this.textviewBack = textviewBack;
  }

  @NonNull
  public static ActivityListExpensesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_list_expenses, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityListExpensesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityListExpensesBinding>inflateInternal(inflater, R.layout.activity_list_expenses, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityListExpensesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_list_expenses, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityListExpensesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityListExpensesBinding>inflateInternal(inflater, R.layout.activity_list_expenses, null, false, component);
  }

  public static ActivityListExpensesBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityListExpensesBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityListExpensesBinding)bind(component, view, R.layout.activity_list_expenses);
  }
}

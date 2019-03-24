package com.example.pc.lifeassistant.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.pc.lifeassistant.R;
import com.example.pc.lifeassistant.adapter.TypeMoneyAdapter;
import com.example.pc.lifeassistant.bean.CapitalInfo;
import com.example.pc.lifeassistant.bean.TypeMoneyInfo;
import com.example.pc.lifeassistant.util.BaseActivity;
import com.example.pc.lifeassistant.util.KeyboardUtil;
import com.example.pc.lifeassistant.util.MoneyKeyBoard;
import com.example.pc.lifeassistant.util.SharedPreferencesHelper;
import com.example.pc.lifeassistant.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.DatePickerDialog.OnDateSetListener;

/**
 * Created by pc on 2018/11/5.
 */

public class AddCapitalActivity extends BaseActivity implements View.OnClickListener {

    private GridView gv_type_money;
    private TextView tv_income;
    private TextView tv_expense;
    private TextView tv_add_capital_plus;
    private TextView tv_add_capital_reduce;
    private TextView tv_add_capital_sum;
    private TextView tv_add_capital_date_year;
    private TextView tv_add_capital_date_day;
    private TextView tv_type_money_show;
    private TextView tv_add_capital_remakes;
    private EditText et_amount;

    public String[] inCome = {"一般", "报销", "工资", "红包", "兼职"
            , "奖金", "投资", "其他"};

    private String[] expense = {"一般", "用餐", "交通", "服饰", "丽人"
            , "日用品", "娱乐", "食材", "零食", "酒水", "住房", "通讯", "家居", "人情", "学习", "医疗", "旅游", "数码"};

    private String remakes;
    private TypeMoneyAdapter typeMoneyAdapter;
    private MoneyKeyBoard keyboard_view;
    private boolean flag_date = false;
    private boolean flag_income = true;
    private boolean flag_sum = true;//是否进行加法运算，加法运算：true;减法运算：false
    private boolean flag_add = true;//是否进行修改操作，修改：false;添加：true

    String incomeOrexpenditure = "收入";
    List<TypeMoneyInfo> list = new ArrayList<>();
    private SharedPreferencesHelper sharedPreferencesHelper;
    String str;
    AVUser user = AVUser.getCurrentUser();
    AVObject capital;
    Double count;
    Double amount;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    Date curDate = new Date(System.currentTimeMillis());
    private String data = "";
    private String ObjectId;
    private int type_position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_capital);
        initSwipeBack();
        init();
        keyBoard();

    }

    @Override
    protected void onStart() {
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        super.onStart();

    }

    public void init() {
        sharedPreferencesHelper = new SharedPreferencesHelper(AddCapitalActivity.this, "CapitalRemakes");
        LinearLayout tv_add_capital_date = (LinearLayout) findViewById(R.id.tv_add_capital_date);
        gv_type_money = (GridView) findViewById(R.id.gv_type_money);
        tv_income = (TextView) findViewById(R.id.tv_income);
        tv_expense = (TextView) findViewById(R.id.tv_expense);
        tv_add_capital_plus = (TextView) findViewById(R.id.tv_add_capital_plus);
        tv_add_capital_reduce = (TextView) findViewById(R.id.tv_add_capital_reduce);
        tv_add_capital_sum = (TextView) findViewById(R.id.tv_add_capital_sum);
        tv_type_money_show = (TextView) findViewById(R.id.tv_type_money_show);
        tv_add_capital_date_year = (TextView) findViewById(R.id.tv_add_capital_date_year);
        tv_add_capital_date_day = (TextView) findViewById(R.id.tv_add_capital_date_day);
        tv_add_capital_remakes = (TextView) findViewById(R.id.tv_add_capital_remakes);
        keyboard_view = (MoneyKeyBoard) findViewById(R.id.keyboard_view);
        et_amount = (EditText) findViewById(R.id.et);

        tv_income.setOnClickListener(this);
        tv_expense.setOnClickListener(this);
        tv_add_capital_plus.setOnClickListener(this);
        tv_add_capital_reduce.setOnClickListener(this);
        tv_add_capital_sum.setOnClickListener(this);
        tv_add_capital_date.setOnClickListener(this);
        tv_add_capital_remakes.setOnClickListener(this);
        add();
        typeMoneyAdapter = new TypeMoneyAdapter(this, list);
        gv_type_money.setAdapter(typeMoneyAdapter);
        gv_type_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type_position = position;
                typeMoneyAdapter.setSeclection(position);
                typeMoneyAdapter.notifyDataSetChanged();
                tv_type_money_show.setText(list.get(position).getTv_type_money());
            }
        });

        et_amount.setEnabled(false);
        et_amount.setFocusable(false);
        et_amount.setKeyListener(null);//重点

    }

    //获取上个页面返回到修改信息
    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(CapitalInfo str) throws ParseException {
        if (str != null) {
            flag_add = false;
            String date = Utils.GMTtoStr(str.getTime() + "");
            ObjectId = str.getObjectId();
            String type = str.getType();
            type_position = str.getType_position();
            String remakes = str.getRemakes();
            String amount = str.getAmount();
            String incomeOrexpenditure = str.getIncomeOrexpenditure();
            flag_income = incomeOrexpenditure.equals("收入");
            if (incomeOrexpenditure.equals("收入")) {
                tv_income.performClick();
                typeMoneyAdapter.setSeclection(type_position);
            } else {
                tv_expense.performClick();
                typeMoneyAdapter.setSeclection(type_position);
            }
            et_amount.setText(amount);
            tv_type_money_show.setText(type);
            sharedPreferencesHelper.put("capital_remakes_key", remakes);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(str.getTime());
            tv_add_capital_date_year.setText(calendar.get(Calendar.YEAR) + "");
            tv_add_capital_date_day.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            init();
        }

    }

    public void add() {
        for (int i = 0; i < inCome.length; i++) {
            TypeMoneyInfo typeMoneyInfo = new TypeMoneyInfo();
            typeMoneyInfo.setTv_type_money(inCome[i]);
            list.add(typeMoneyInfo);
        }
    }

    public void add1() {
        for (int i = 0; i < expense.length; i++) {
            TypeMoneyInfo typeMoneyInfo = new TypeMoneyInfo();
            typeMoneyInfo.setTv_type_money(expense[i]);
            list.add(typeMoneyInfo);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_income:
                flag_income = true;
                tv_income.setTextColor(getResources().getColor(R.color.toolbar_color));
                tv_expense.setTextColor(getResources().getColor(R.color.text_color_default));
                list.clear();
                add();
                typeMoneyAdapter = new TypeMoneyAdapter(this, list);
                gv_type_money.setAdapter(typeMoneyAdapter);

                break;
            case R.id.tv_expense:
                flag_income = false;
                tv_expense.setTextColor(getResources().getColor(R.color.toolbar_color));
                tv_income.setTextColor(getResources().getColor(R.color.text_color_default));
                list.clear();
                add1();
                typeMoneyAdapter = new TypeMoneyAdapter(this, list);
                gv_type_money.setAdapter(typeMoneyAdapter);
                break;
            case R.id.tv_add_capital_plus:
                String money_plus = et_amount.getText().toString();
                if (null == money_plus || money_plus.equals("")) {
                    amount = 0.0;
                } else {
                    amount = Double.parseDouble(money_plus);
                }
                flag_sum = true;
                count = amount;
                et_amount.getText().clear();
                tv_add_capital_plus.setVisibility(View.GONE);
                tv_add_capital_sum.setVisibility(View.VISIBLE);
                tv_add_capital_reduce.setVisibility(View.GONE);
                break;

            case R.id.tv_add_capital_reduce:
                String money_reduce = et_amount.getText().toString();
                if (null == money_reduce || money_reduce.equals("")) {
                    amount = 0.0;
                } else {
                    amount = Double.parseDouble(money_reduce);
                }
                flag_sum = false;
                count = amount;
                et_amount.getText().clear();
                tv_add_capital_plus.setVisibility(View.GONE);
                tv_add_capital_sum.setVisibility(View.VISIBLE);
                tv_add_capital_reduce.setVisibility(View.GONE);
                break;
            case R.id.tv_add_capital_sum:
                String money_sum = et_amount.getText().toString();
                if (null == money_sum || money_sum.equals("")) {
                    amount = 0.0;
                } else {
                    amount = Double.parseDouble(money_sum);
                }
                if (flag_sum) {
                    count += amount;
                } else {
                    count -= amount;
                }
                et_amount.setText(String.valueOf(count));
                tv_add_capital_sum.setVisibility(View.GONE);
                tv_add_capital_plus.setVisibility(View.VISIBLE);
                tv_add_capital_reduce.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_add_capital_date:
                date();
                break;
            case R.id.tv_add_capital_remakes:
                Intent intent = new Intent(AddCapitalActivity.this, AddCapitalRemakesDialog.class);
                startActivityForResult(intent, 0);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            str = sharedPreferencesHelper.getSharedPreference("capital_remakes_key", "").toString().trim();
        } else {
            remakes = data.getStringExtra("data");
        }
    }

    public void keyBoard() {
        final KeyboardUtil keyboardUtil = new KeyboardUtil(AddCapitalActivity.this);
        keyboardUtil.attachTo(et_amount);
        keyboardUtil.setOnOkClick(new KeyboardUtil.OnOkClick() {
            @Override
            public void onOkClick() {
                Conditionaljudgement();
            }
        });
        keyboardUtil.setOnCancelClick(new KeyboardUtil.onCancelClick() {
            @Override
            public void onCancellClick() {
                et_amount.getText().clear();


            }
        });

        et_amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.attachTo(et_amount);
                return false;
            }
        });

    }

    private void Conditionaljudgement() {
        if (et_amount.getText().toString().equals("")) {
            Toast.makeText(this, "金额为0", Toast.LENGTH_SHORT).show();
        } else if (flag_date) {
            Toast.makeText(this, "日期错误", Toast.LENGTH_SHORT).show();
        } else if (data.equals("")) {
            data = formatter.format(curDate);
            if (flag_income) {
                incomeOrexpenditure = "收入";
            } else {
                incomeOrexpenditure = "支出";
            }
            if (null == str || str.equals("")) {
                remakes = "暂无备注";

                addCapital();


            } else {
                remakes = str;

                addCapital();


            }
        } else {
            if (flag_income) {
                incomeOrexpenditure = "收入";
            } else {
                incomeOrexpenditure = "支出";
            }
            if (null == str || str.equals("")) {
                remakes = "暂无备注";

                addCapital();


            } else {
                remakes = str;

                addCapital();


            }
        }
    }


    private void addCapital() {
        if (flag_add) {
            capital = new AVObject("Capital");
        } else {
            capital = AVObject.createWithoutData("Capital", ObjectId);
        }

        capital.put("amount", et_amount.getText().toString());
        capital.put("time", StrToDate(data));
        capital.put("remakes", remakes);
        capital.put("type", tv_type_money_show.getText().toString());
        capital.put("type_position", type_position);
        capital.put("incomeOrexpenditure", incomeOrexpenditure);
        capital.put("user_id", user.getObjectId());
        capital.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(AddCapitalActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    sharedPreferencesHelper.remove("capital_remakes_key");
                    finish();

                } else {
                    Toast.makeText(AddCapitalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                    if (Utils.isNetworkConnected(AddCapitalActivity.this)) {
                        Toast.makeText(AddCapitalActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void date() {
        Calendar c = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(AddCapitalActivity.this,
                // 绑定监听器
                new OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        data = year + "/" + month + "/" + dayOfMonth;
                        if (getTimeCompareSize(StrToDate(data)) > 1) {
                            flag_date = false;
                            tv_add_capital_date_year.setText(year + "");
                            tv_add_capital_date_day.setText(month + "/" + dayOfMonth);
                        } else {
                            flag_date = true;
                            Toast.makeText(AddCapitalActivity.this, "所选时间超出范围", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                // 设置初始日期
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH)).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferencesHelper.remove("capital_remakes_key");
        if (EventBus.getDefault().isRegistered(this)) { //加上判断
            //移除所有的粘性事件
            EventBus.getDefault().removeAllStickyEvents();
            //解除注册
            EventBus.getDefault().unregister(this);
        }
    }
}

package com.sty.object.box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sty.object.box.app.MyApplication;
import com.sty.object.box.model.Customer;
import com.sty.object.box.model.MyObjectBox;
import com.sty.object.box.model.Order;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etId;
    private EditText etName;
    private EditText etAge;
    private Button btnAdd;
    private Button btnAdd3;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnSetRelation;
    private Button btnDeleteRelation;
    private ListView lvListView;

    private String id;
    private String name;
    private String age;

    private Box<Customer> customerBox;
    private List<Customer> mDataList = new ArrayList<>();
    private MyAdapter adapter;

    private long customerId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();

        queryAll();

        Toast.makeText(this, "insert db success!", Toast.LENGTH_SHORT).show();
    }

    private void initViews(){
        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd3 = findViewById(R.id.btn_add3);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        btnSetRelation = findViewById(R.id.btn_set_relation);
        btnDeleteRelation = findViewById(R.id.btn_delete_relation);
        lvListView = findViewById(R.id.lv_list_view);

        customerBox = MyApplication.getmBoxStore().boxFor(Customer.class);
        adapter = new MyAdapter(this, mDataList);
        lvListView.setAdapter(adapter);
    }

    private void setListeners(){
        btnAdd.setOnClickListener(this);
        btnAdd3.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSetRelation.setOnClickListener(this);
        btnDeleteRelation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                addOne();
                queryAll();
                break;
            case R.id.btn_add3:
                addThree();
                queryAll();
                break;
            case R.id.btn_delete:
                delete();
                queryAll();
                break;
            case R.id.btn_update:
                update();
                queryAll();
                break;
            case R.id.btn_set_relation:
                setRelation();
                queryAll();
                break;
            case R.id.btn_delete_relation:
                deleteRelation();
                queryAll();
                break;
            default:
                break;
        }
    }

    private void addOne(){
        name = etName.getText().toString().trim();
        age = etAge.getText().toString().trim();
        if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(age)) {
            Customer customer = new Customer();
            if(!TextUtils.isEmpty(name)){
                customer.setName(name);
            }
            if(!TextUtils.isEmpty(age)){
                customer.setAge(Integer.parseInt(age));
            }
            customerBox.put(customer);
            Toast.makeText(this, "add success!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addThree(){
        List<Customer> customers = new ArrayList<>();

        name = etName.getText().toString().trim();
        age = etAge.getText().toString().trim();
        if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(age)) {
            for (int i = 0; i < 3; i++) {
                Customer customer = new Customer();
                if(!TextUtils.isEmpty(name)){
                    customer.setName(name);
                }
                if(!TextUtils.isEmpty(age)){
                    customer.setAge(Integer.parseInt(age));
                }
                customers.add(customer);
            }
            customerBox.put(customers);
            Toast.makeText(this, "add 3 success!", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        id = etId.getText().toString().trim();
        if(!TextUtils.isEmpty(id)) {
            customerBox.remove(Long.parseLong(id));
            Toast.makeText(this, "remove " + id + " success!", Toast.LENGTH_SHORT).show();
        }
    }

    private void update(){
        id = etId.getText().toString().trim();
        name = etName.getText().toString().trim();
        age = etAge.getText().toString().trim();
        if(!TextUtils.isEmpty(id)) {
            Customer customer = new Customer();
            customer.setId(Long.parseLong(id));
            if(!TextUtils.isEmpty(name)){
                customer.setName(name);
            }
            if(!TextUtils.isEmpty(age)){
                customer.setAge(Integer.parseInt(age));
            }
            customerBox.put(customer);
            Toast.makeText(this, "update success!", Toast.LENGTH_SHORT).show();
        }
    }

    private void queryAll(){
//        QueryBuilder<Customer> builder = customerBox.query();
//        mDataList = builder.build().find();
        mDataList = customerBox.getAll();  //得到所有的数据
        adapter.setDataList(mDataList);
    }

    private void setRelation(){
        Customer customer = new Customer("mandy", 17);
        customer.getOrder().add(new Order("order1"));
        customer.getOrder().add(new Order("order2"));
        customer.getOrder().add(new Order("order3"));

        customerId = customerBox.put(customer); //put customer and orders

        Customer customer1 = customerBox.get(customerId);
        for(Order order : customer1.getOrder()){
            Log.i("sty", "order name: " + order.getName());
        }

        Toast.makeText(this, "build relation success!", Toast.LENGTH_SHORT).show();
    }

    private void deleteRelation(){
        Customer customer = customerBox.get(customerId);
        long orderId = 0;
        for(Order order : customer.getOrder()){
            orderId = order.getId();
            break;
        }

        if(orderId != 0) {
            customer.getOrder().remove(orderId);
            customerBox.put(customer);

            MyApplication.getmBoxStore().boxFor(Order.class).remove(orderId);
        }
    }
}

package com.example.goingdutch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Add extends AppCompatActivity implements View.OnClickListener {
    EditText etTitle, etPerson;
    ImageButton ibPerson, ibAdd;
    Button btnResult;
    public static ArrayList<String> list, content;
    String sendPerson, money, attendString;
    ListView lvList;
    public static int moneySelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        content = new ArrayList<>();
        list = new ArrayList<>();
        list.add("나");

        etTitle = (EditText) findViewById(R.id.etTitle);
        etPerson = (EditText) findViewById(R.id.etPerson);

        ibPerson = (ImageButton) findViewById(R.id.ibPerson);
        ibPerson.setOnClickListener(this);

        ibAdd = (ImageButton) findViewById(R.id.ibAdd);
        ibAdd.setOnClickListener(this);

        lvList = (ListView) findViewById(R.id.lvList);

        Spinner spMoney = (Spinner) findViewById(R.id.spMoney);
        ArrayList<String> listMoney = new ArrayList<>();
        listMoney.add("10원단위 올림");
        listMoney.add("100원단위 올림");
        listMoney.add("10원단위 내림");
        listMoney.add("100원단위 내림");
        ArrayAdapter<String> adapterMoney = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listMoney);
        spMoney.setAdapter(adapterMoney);
        spMoney.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moneySelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnResult = (Button) findViewById(R.id.btnResult);
        btnResult.setOnClickListener(this);
    }

    public void personList() {
        StringTokenizer tokenizer = new StringTokenizer(etPerson.getText().toString(), " ");
        boolean personChk = false;

        while (tokenizer.hasMoreTokens()) {
            String person = tokenizer.nextToken();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(person)) {
                    personChk = true;
                }
            }

            if (personChk) {
                personChk = false;
            } else {
                list.add(person);
            }
        }
    }

    public void onClick(View view) {
        if (view == ibPerson) {
            if (etPerson.getText().toString().isEmpty() || etPerson.getText().toString().equals(" ")) {
                Toast.makeText(getApplicationContext(), "참여자를 입력하세요.", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "참여자를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                list = new ArrayList<String>();
                list.add("나");
                personList();
            }
        } else if (view == ibAdd) {
            AlertDialog.Builder digAdd = new AlertDialog.Builder(Add.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View root = inflater.inflate(R.layout.digadd, null);
            digAdd.setView(root);

            final ListView lvAttend = (ListView) root.findViewById(R.id.lvAttend);
            final ArrayAdapter<String> adapterAttend = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, list);
            lvAttend.setAdapter(adapterAttend);
            lvAttend.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            for (int i = 0; i < adapterAttend.getCount(); i++) {
                lvAttend.setItemChecked(i, true);
            }

            final EditText etMoney = (EditText) root.findViewById(R.id.etMoney);
            Spinner spSend = (Spinner) root.findViewById(R.id.spSend);

            final ArrayAdapter<String> adapterPerson = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
            spSend.setAdapter(adapterPerson);
            spSend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sendPerson = list.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            digAdd.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    money = etMoney.getText().toString();
                    if (list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "결제자를 입력하세요.", Toast.LENGTH_SHORT).show();

                    } else if (money.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "금액을 입력하세요.", Toast.LENGTH_SHORT).show();

                    } else {
                        SparseBooleanArray checkedItems = lvAttend.getCheckedItemPositions();
                        attendString = "";
                        for (int i = 0; i < adapterAttend.getCount(); i++) {
                            if (checkedItems.get(i)) {
                                attendString += list.get(i) + " ";
                            }
                        }

                        if (attendString.equals("")) {
                            Toast.makeText(getApplicationContext(), "참여자를 선택하세요.", Toast.LENGTH_SHORT).show();

                        } else {
                            final ArrayAdapter<String> adapterList = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, content);
                            content.add("결제자:" + sendPerson + "  금액:" + money + "  참여자:" + attendString);

                            lvList.setAdapter(adapterList);
                            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                    AlertDialog.Builder digUpdate = new AlertDialog.Builder(Add.this);
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View root = inflater.inflate(R.layout.digupdate, null);
                                    digUpdate.setView(root);


                                    final ListView lvAttend = (ListView) root.findViewById(R.id.lvAttend);
                                    final ArrayAdapter<String> adapterAttend = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, list);
                                    lvAttend.setAdapter(adapterAttend);
                                    lvAttend.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                                    String cont = content.get(position);
                                    StringTokenizer tokenizer1 = new StringTokenizer(cont, " ");

                                    while (tokenizer1.hasMoreTokens()) {
                                        String str = tokenizer1.nextToken();

                                        if (str.substring(0, 2).equals("결제")) {
                                            sendPerson = str.substring(4, str.length());
                                        } else if (str.substring(0, 2).equals("금액")) {
                                            money = str.substring(3, str.length());
                                        }
                                    }

                                    if (cont.indexOf("참여자:") > -1) {
                                        int index = cont.indexOf("참여자:");
                                        attendString = cont.substring(index + 4, cont.length());
                                    }

                                    Log.d("young", attendString);
                                    StringTokenizer tokenizer2 = new StringTokenizer(attendString, " ");
                                    while (tokenizer2.hasMoreTokens()) {
                                        String str = tokenizer2.nextToken();

                                        for (int i = 0; i < adapterAttend.getCount(); i++) {
                                            if (str.equals(list.get(i))) {
                                                lvAttend.setItemChecked(i, true);
                                                break;
                                            }
                                        }
                                    }

                                    final EditText etMoney = (EditText) root.findViewById(R.id.etMoney);
                                    etMoney.setText(money);

                                    Spinner spSend = (Spinner) root.findViewById(R.id.spSend);
                                    spSend.setAdapter(adapterPerson);

                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).equals(sendPerson)) {
                                            spSend.setSelection(i);
                                            break;
                                        }
                                    }


                                    spSend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            sendPerson = list.get(position);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    digUpdate.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            content.remove(position);
                                            lvList.clearChoices();
                                            adapterList.notifyDataSetChanged();
                                        }
                                    });

                                    digUpdate.setNegativeButton("수정", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (list.size() == 0) {
                                                Toast.makeText(getApplicationContext(), "결제자를 입력하세요.", Toast.LENGTH_SHORT).show();

                                            } else if (money.isEmpty()) {
                                                Toast.makeText(getApplicationContext(), "금액을 입력하세요.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                SparseBooleanArray checkedItems = lvAttend.getCheckedItemPositions();
                                                attendString = "";
                                                for (int i = 0; i < adapterAttend.getCount(); i++) {
                                                    if (checkedItems.get(i)) {
                                                        attendString += list.get(i) + " ";
                                                    }
                                                }

                                                if (attendString.equals("")) {
                                                    Toast.makeText(getApplicationContext(), "참여자를 선택하세요.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    money = etMoney.getText().toString();
                                                    content.set(position, "결제자:" + sendPerson + "  금액:" + money + "  참여자:" + attendString);

                                                    lvList.clearChoices();
                                                    adapterList.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    });

                                    digUpdate.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            lvList.clearChoices();
                                            adapterList.notifyDataSetChanged();
                                        }
                                    });

                                    digUpdate.show();
                                }
                            });
                            lvList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                        }
                    }
                }
            });

            digAdd.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            digAdd.show();
        } else if (view == btnResult) {
            Info info = new Info(etTitle.getText().toString(), etPerson.getText().toString(), content);
            Intent intent = new Intent(getApplicationContext(), Result.class);
            intent.putExtra("info", info);
            startActivityForResult(intent, 10);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Info info = (Info) data.getSerializableExtra("info");
            etTitle.setText(info.getTitle());
            etPerson.setText(info.getList());
            content.clear();
            for(int i=0;i<info.getContent().size();i++){
                content.add(info.getContent().get(i));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
            finish();
        }

        return false;
    }
}

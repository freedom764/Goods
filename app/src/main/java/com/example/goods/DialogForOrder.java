package com.example.goods;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DialogForOrder extends AppCompatDialogFragment {
    private EditText amountfor;
    private DialogListener listener;
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_order,null);
        builder.setView(view)
                .setTitle("Заказ товара")
                .setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("ГОТОВО", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String amount = amountfor.getText().toString();
                        if (amountfor.length() == 0) {
                            Toast.makeText(getActivity(), "Вам нужно заполнить поле", Toast.LENGTH_SHORT).show();


                        } else {
                            try {

                                int amount1 = Integer.parseInt(amount);
                                listener.applytext(amount1);

                            } catch (NumberFormatException e) {
                                Toast.makeText(getActivity(), "Введите действительные данные", Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                });

        amountfor = view.findViewById(R.id.amountfororder);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"ошибка");
        }
    }

    public interface DialogListener{
        void applytext( int amount1);
    }
}



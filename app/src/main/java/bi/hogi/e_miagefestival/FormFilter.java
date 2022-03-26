package bi.hogi.e_miagefestival;

import android.app.Dialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Date;

public class FormFilter extends Dialog {
    private final MainActivity context;
    private  Button btn_cancel, btn_submit;
    private Spinner spinner_filter_jour, spinner_filter_scene;
    private Date du, au;

    public FormFilter(MainActivity context) {
        super(context, R.style.CustomAlertDialogTheme);
        setContentView(R.layout.dialog_filter);
        this.context = context;

        spinner_filter_jour = findViewById(R.id.spinner_filter_jour);
        spinner_filter_scene = findViewById(R.id.spinner_filter_scene);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        btn_cancel.setOnClickListener(view -> dismiss());
        btn_submit.setOnClickListener(view -> {
            context.filter(
                spinner_filter_jour.getSelectedItem().toString(),
                spinner_filter_scene.getSelectedItem().toString()
            );
            dismiss();
        });

        fillSpinners();
    }
    private void fillSpinners() {
        ArrayAdapter adapter_jour = new ArrayAdapter(
            context,
            R.layout.support_simple_spinner_dropdown_item,
            context.jours);
        spinner_filter_jour.setAdapter(adapter_jour);

        ArrayAdapter adapter_scene = new ArrayAdapter(
            context,
            R.layout.support_simple_spinner_dropdown_item,
            context.scenes);
        spinner_filter_scene.setAdapter(adapter_scene);
    }
}
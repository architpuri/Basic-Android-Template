package in.themoneytree.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.btn_shareList)
    Button btnShareList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnShareList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bit.do/Capstone_stocks"));
                startActivity(browserIntent);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Link Not Working",Toast.LENGTH_SHORT);
                }
            }
        });
    }
}

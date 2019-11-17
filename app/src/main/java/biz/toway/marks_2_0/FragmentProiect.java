package biz.toway.marks_2_0;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentProiect extends Fragment
{
    String[] marks;
    TextView lbCount;
    TextView lbGeneral;
    EditText tbMarks;
    EditText tbExamen;
    EditText tbProiect;
    EditText tbMedia;

    View tileMedia;
    View tileProject;
    View tileExamen;
    View tileGeneral;

    static boolean lockEventMarks = false;
    static boolean lockEventMedia = false;

    public FragmentProiect() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_proiect, container, false);
        lbCount = (TextView) rootView.findViewById(R.id.lbCount);
        lbGeneral = (TextView) rootView.findViewById(R.id.lbGenerala);

        tileMedia = rootView.findViewById(R.id.tileMedia);
        tileProject = rootView.findViewById(R.id.tileProject);
        tileExamen = rootView.findViewById(R.id.tileExamen);
        tileGeneral = rootView.findViewById(R.id.tileGeneral);

        tbMarks = (EditText) rootView.findViewById(R.id.tbMarks);
        tbMarks.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(!lockEventMarks)
                {
                    String s = editable.toString().replaceAll("#", " ").replace("*", " ")
                            .replaceAll("(.)", "$1 ")
                            .replaceAll("\\s{2,}", " ")
                            .replaceAll("1 0", "10")
                            .replaceAll(" 0 ", " ")
                            .replaceAll("\\s{2,}", " ")
                            .trim();
                    if(s.startsWith("0")) s = "";

                    lockEventMarks = true;
                    tbMarks.setText(s);
                }
                else
                {
                    lockEventMarks = false;
                    int length = tbMarks.length();
                    tbMarks.setSelection(length);

                    calculateMedia();
                    calculateGeneralMarks();
                }
            }
        });

        tbExamen = (EditText) rootView.findViewById(R.id.tbExamen);
        tbExamen.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean focus)
            {
                if(focus) tbExamen.setText("");
            }
        });
        tbExamen.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                tileExamen.setBackgroundColor(getResources().getColor(R.color.colorTile));

                if(tbExamen.getText().toString().length() > 0)
                {
                    if(Integer.valueOf(tbExamen.getText().toString()) > 10)
                    {
                        tbExamen.setText("10");
                        tbExamen.selectAll();
                    }

                    if(Integer.valueOf(tbExamen.getText().toString()) < 5)
                        tileExamen.setBackgroundColor(getResources().getColor(R.color.colorError));

                    if(Integer.valueOf(tbExamen.getText().toString()) == 0)
                    {
                        tbExamen.setText("1");
                        tbExamen.selectAll();
                    }
                }
                calculateGeneralMarks();
            }
        });

        tbProiect = (EditText) rootView.findViewById(R.id.tbProiect);
        tbProiect.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean focus)
            {
                if(focus) tbProiect.setText("");
            }
        });
        tbProiect.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable)
            {
                tileProject.setBackgroundColor(getResources().getColor(R.color.colorTile));

                if(tbProiect.getText().toString().length() > 0)
                {
                    if(Integer.valueOf(tbProiect.getText().toString()) > 10)
                    {
                        tbProiect.setText("10");
                        tbProiect.selectAll();
                    }

                    if(Integer.valueOf(tbProiect.getText().toString()) < 5)
                        tileProject.setBackgroundColor(getResources().getColor(R.color.colorError));

                    if(Integer.valueOf(tbProiect.getText().toString()) == 0)
                    {
                        tbProiect.setText("1");
                        tbProiect.selectAll();
                    }
                }
                calculateGeneralMarks();
            }
        });

        tbMedia = (EditText) rootView.findViewById(R.id.tbMedia);
        tbMedia.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean focus)
            {
                if(focus)
                    tbMarks.setText("");
                else
                {
                    String s = tbMedia.getText().toString();
                    if(s.length() > 0)
                        tbMedia.setText((s.replaceAll(",", "") + "000").substring(0, 4));
                }
            }

            public void tmp() {}
        });
        tbMedia.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(!lockEventMedia)
                {
                    String s = editable.toString().replaceAll(",", "").replaceAll("\\.", "");
                    Log.d("----------------------", s);
                    if(s.startsWith("10"))
                        s = "10";
                    else
                    {
                        if(s.length() >= 2)
                        {

                            String nota = String.valueOf(s.charAt(0));
                            s = s.replaceFirst(nota, nota + ",");
                        }

                        if(s.startsWith("0")) s = s.replaceFirst("0", "1");

                        if(s.length() > 4) s = s.substring(0, 4);
                    }
                    lockEventMedia = true;
                    tbMedia.setText(s);
                }
                else
                {
                    lockEventMedia = false;
                    int length = tbMedia.length();
                    tbMedia.setSelection(length);

                    calculateGeneralMarks();

                    if(tbMedia.getText().toString().length() > 0 && Float.valueOf(tbMedia.getText().toString().replaceAll(",", ".")) < 5)
                        tileMedia.setBackgroundColor(getResources().getColor(R.color.colorError));
                    else
                        tileMedia.setBackgroundColor(getResources().getColor(R.color.colorTile));
                }
            }
        });
        return rootView;
    }

    private void calculateGeneralMarks()
    {
        tileGeneral.setBackgroundColor(getResources().getColor(R.color.colorTile));
        lbGeneral.setText("");

        float media = tbMedia.getText().toString().length() > 0 ? Float.valueOf(tbMedia.getText().toString().replace(',', '.')) : 0;
        float proiect = tbProiect.getText().toString().length() > 0 ? Float.valueOf(tbProiect.getText().toString().replace(',', '.')) : 0;
        float examen = tbExamen.getText().toString().length() > 0 ? Float.valueOf(tbExamen.getText().toString().replace(',', '.')) : 0;
        float res = 0;

        // 50/30/20
        if(proiect > 0 && examen > 0 && media > 0)
            res = (media * 0.5f) + (examen * 0.3f) + (proiect * 0.2f);
        else
            // 60/40
            if(examen > 0 && media > 0)
                res = (media * 0.6f) + (examen * 0.4f);

        if(res > 0)
        {
            String tmp = String.valueOf(res).replace('.', ',');
            int p = tmp.indexOf(",");
            if(p > 0)
            {
                tmp += "00";
                tmp = tmp.substring(0, p + 3);
            }
            else
                tmp += ",00";
            lbGeneral.setText(tmp);
            if(res < 5) tileGeneral.setBackgroundColor(getResources().getColor(R.color.colorError));
        }
    }

    private void calculateMedia()
    {
        tbMedia.setText("");
        lbCount.setText("0");

        if(tbMarks.getText().toString().length() == 0) return;

        marks = tbMarks.getText().toString().split("[\\s\\,\\.\\-\\+\\#\\*]");
        float media = 0;
        int count = 0;
        for(String m : marks)
        {
            if(m.length() > 0)
            {
                media += Float.valueOf(m);
                count++;
            }
        }
        if(count > 0)
            media /= count;
        if(count > 99) count = 99;

        String m = String.valueOf(media).replace('.', ',');
        int p = m.indexOf(",");
        if(p > 0)
        {
            m += "00";
            m = m.substring(0, p + 3);
        }
        else
            m += ",00";

        lbCount.setText(String.valueOf(count));
        tbMedia.setText(String.valueOf(m));
    }

    public void clearFragment()
    {
        lbCount.setText("0");
        tbMedia.setText("");
        lbGeneral.setText("");
        tbExamen.setText("");
        tbProiect.setText("");
        tbMarks.setText("");

        tileMedia.setBackgroundColor(getResources().getColor(R.color.colorTile));
        tileProject.setBackgroundColor(getResources().getColor(R.color.colorTile));
        tileExamen.setBackgroundColor(getResources().getColor(R.color.colorTile));
        tileGeneral.setBackgroundColor(getResources().getColor(R.color.colorTile));

        tbMarks.requestFocus();
    }
}

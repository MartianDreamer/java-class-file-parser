package com.github.martiandreamer;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModifiedUtf8ParserTest {

    @Test
    void test() {
        String expected =
                "ABCdef123!@#$\0" +
                        "¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿" +
                        "ĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğ" +
                        "ΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΡρΣσςΤτΥυΦφΧχΨψΩω" +
                        "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя" +
                        "ا ب ت ث ج ح خ د ذ ر ز س ش ص ض ط ظ ع غ" +
                        "אבגדהוזחטיכךלמםנןסעפףצץקרשת" +
                        "हिन्दी देवनागरी" +
                        "中文 日本語 한국어" +
                        "漢字かな漢字ハングル" +
                        "😀😂🤦‍♂️👨‍👩‍👧‍👦🏳️‍🌈" +
                        "Cͨ͆̽ͣ̽ͥ̋̑oͮͨͦ̍͌ͮ͒mͪͣͩ͐͑͒bͯͧ̚iͨͣͩ̾nͭͬ̆ͩ͌ͩiͨ̾͗ͩͮnͭͬ̆ͩ͌ͩgͪͣͩ͐͑͒" +
                        "Chúc bạn một ngày tốt lành" +
                        "\uD834\uDD1E" + // 𝄞 G clef
                        "\uD83D\uDE00" + // 😀
                        "\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC65\uDB40\uDC6E\uDB40\uDC67\uDB40\uDC7F" + // 🏴󠁧󠁢󠁥󠁮󠁧󠁿 Flag: England
                        "\uDB80\uDC00" + // U+20000 (CJK Extension B)
                        "\uDBFF\uDFFF";  // U+10FFFF (last Unicode code point)
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeShort(0);
            dos.writeUTF(expected);
            byte[] bytes = baos.toByteArray();
            String actual = new ModifiedUtf8Parser(bytes, 0, 4).parse();
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
package com.example.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;
import org.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Controller
public class hwpFileController {


    /**
     * 대충 그냥
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/")
    public void demoHome(HttpServletRequest request, HttpServletResponse response) throws Exception {

         response.setContentType("application/octet-stream");
        //response.setHeader("Content-Disposition", "attachment; filename=\"hello.jpg\"");

        //byte[] bytes = request.getInputStream().readAllBytes();
        response.setContentType("application/json");
        response.setCharacterEncoding("euc-kr");
        //byte[] bytes = "123".getBytes(StandardCharsets.UTF_8);

        Map<String, Object> param = new HashMap<>();
        param.put("name", "mike");
        param.put("age", 13);
        param.put("class", "B");

        JSONObject jsonf = new JSONObject(param);
        String paramStr = jsonf.toString();


        //HWPfile 객체로 생성
        HWPFile hwpFile = BlankFileMaker.make();

        Section sec = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph firstParagraph = sec.getParagraph(0);
        firstParagraph.getText().addString(paramStr);



        response.setHeader("Content-Disposition", "attachment; filename=\"text.hwp\"");

        //response.setCharacterEncoding("UTF-8");

        //FileCopyUtils.copy(paramStr.getBytes(), response.getOutputStream());
        HWPWriter.toStream(hwpFile, response.getOutputStream());




    }
}

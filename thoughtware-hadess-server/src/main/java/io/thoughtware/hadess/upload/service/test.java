package io.thoughtware.hadess.upload.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.*;
import java.time.Instant;
import java.util.*;

public class test {

    public static void main(String[] args) throws Exception {
        String path1="/Users/limingliang/tiklab/thoughtware-hadess/repository/FKKpxI4vNbyc/list";

        String path="/Users/limingliang/tiklab/thoughtware-hadess/repository/FKKpxI4vNbyc/github.com/deckarep/golang-set/@v/list";
        FileWriter writer = new FileWriter(path);
        writer.write("dcscascasata");
        writer.close();

        String a="/Users/limingliang/other/helm/helm-local 2/Chart.yaml";
        String a2="/Users/limingliang/tiklab/thoughtware-hadess/repository/Xerijah0U7bd/index.yaml";
        File file = new File(a2);
        long length = file.length();

        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        Map<String, Object> data =  yaml.load(new FileInputStream(a2));
        Map<String, Object> newDatabaseMap = new LinkedHashMap<>(data);

        Map<String, Object> map = new LinkedHashMap<>();

        LinkedHashMap<String, Object> entries = (LinkedHashMap<String, Object>) newDatabaseMap.get("entries");



        /*List<Map> arrayList = new ArrayList<>();
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("name","helm-local");
        map2.put("version","0.1.0");
        arrayList.add(map2);
        if (ObjectUtils.isEmpty(entries)){
            entries.put("helm-local",arrayList);
        }
        newDatabaseMap.put("entries", entries);
        System.out.println("查询结果3："+map);*/

     /*   for (Map.Entry<String, Object> entry : newDatabaseMap.entrySet()) {
            if (entry.getKey().equals("description")) {
                String now = Instant.now().toString();
                map.put("created",now);
            }
            map.put(entry.getKey(),newDatabaseMap.get(entry.getKey()));
            if (entry.getKey().equals("description")) {
                map.put("digest","002bd7ee72f750929c0b5f09d3ad687ed0e9e6349a34255012c7a115a4c003a2");
            }
            System.out.println("cas");
        }
*/
        // 更新 'database' 的值
      /*  FileWriter writer = new FileWriter("/Users/limingliang/tiklab/thoughtware-hadess/repository/Xerijah0U7bd/helm-local/index.yaml");
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml1 = new Yaml(options);
        yaml1.dump(newDatabaseMap, writer);*/

        System.out.println("查询结果3："+map);

    }

}

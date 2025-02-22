package com.duplessis.etienne.patchmaker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Patch {
    private String patchNumber;
    private String patchName;
    private String patchDescription;
    private String patchVersion;
    private List<String> procedure;
    private List<String> function;
    private List<String> type;
    private List<String> apex;
    private List<String> sql;
    private List<String> view;
    private List<String> trigger;
    private List<String> table;
}

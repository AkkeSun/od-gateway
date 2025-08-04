package com.example.odgateway.infrastructure.validation.groups;

import com.example.odgateway.infrastructure.validation.groups.ValidationGroups.CustomGroups;
import com.example.odgateway.infrastructure.validation.groups.ValidationGroups.NotBlankGroups;
import com.example.odgateway.infrastructure.validation.groups.ValidationGroups.SizeGroups;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroups.class, SizeGroups.class, CustomGroups.class})
public interface ValidationSequence {

}

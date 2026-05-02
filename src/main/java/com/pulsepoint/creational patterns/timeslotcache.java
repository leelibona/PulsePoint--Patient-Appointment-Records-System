package com.pulsepoint.creational_patterns.prototype;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Pattern — TimeSlot Cache
 *
 * Purpose: Stores pre-configured TimeSlot templates and clones them
 * when new slots are needed, avoiding costly repeated initialization.
 *
 * Use Case in PulsePoint: Doctors typically have recurring time slots
 * (e.g., 30-minute slots every day). Instead of creating each slot from
 * scratch, the system clones a pre-configured template and adjusts the date.
 */
public class TimeSlotCache {

    private static Map<String, TimeSlotPrototype> slotTemplates = new HashMap<>();

    static {
        // Pre-configure standard slot templates
        slotTemplates.put("Morning30", new TimeSlotPrototype(
            "TEMPLATE-MORNING-30",
            "TEMPLATE",
            LocalDateTime.now().withHour(8).withMinute(0),
            LocalDateTime.now().withHour(8).withMinute(30)
        ));

        slotTemplates.put("Afternoon30", new TimeSlotPrototype(
            "TEMPLATE-AFTERNOON-30",
            "TEMPLATE",
            LocalDateTime.now().withHour(14).withMinute(0),
            LocalDateTime.now().withHour(14).withMinute(30)
        ));

        slotTemplates.put("Evening30", new TimeSlotPrototype(
            "TEMPLATE-EVENING-30",
            "TEMPLATE",
            LocalDateTime.now().withHour(17).withMinute(0),
            LocalDateTime.now().withHour(17).withMinute(30)
        ));
    }

    /**
     * Returns a cloned TimeSlot based on the template type.
     * The clone gets a new unique ID and can be customised after cloning.
     */
    public static TimeSlotPrototype getSlot(String templateType, String doctorId) {
        TimeSlotPrototype template = slotTemplates.get(templateType);
        if (template == null) {
            throw new IllegalArgumentException("Unknown slot template: " + templateType);
        }
        TimeSlotPrototype cloned = template.clone();
        cloned.setDoctorId(doctorId);
        cloned.setSlotId("SLOT-" + System.currentTimeMillis());
        System.out.println("Cloned " + templateType + " slot for doctor: " + doctorId);
        return cloned;
    }
}
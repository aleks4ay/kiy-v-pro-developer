package org.aleks4ay.developer.controller;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ControllerKbTest {


    @Test
    public void shouldReturnEmptyString_whenLeftMoreThenDay() {
        //Left 2 days
        LocalDate dayEnd = LocalDate.now().plusDays(2);
        String actual = ControllerKb.applyRowStyleTableView(dayEnd);
        assertEquals("", actual);
    }

    @Test
    public void shouldReturnRed1_whenLeftOneDay() {
        LocalDate dayEnd = LocalDate.now().plusDays(1);
        String actual = ControllerKb.applyRowStyleTableView(dayEnd);
        assertEquals("red1", actual);
    }

    @Test
    public void shouldReturnRed2_whenTheSameDay() {
        LocalDate dayEnd = LocalDate.now();
        String actual = ControllerKb.applyRowStyleTableView(dayEnd);
        assertEquals("red2", actual);
    }

    @Test
    public void shouldReturnRed3_whenOverdueNotMoreThenThreeDays() {
        LocalDate dayEnd = LocalDate.now().minusDays(1);
        LocalDate dayEnd2 = LocalDate.now().minusDays(3);

        String actual = ControllerKb.applyRowStyleTableView(dayEnd);
        String actual2 = ControllerKb.applyRowStyleTableView(dayEnd2);

        assertEquals("red3", actual);
        assertEquals("red3", actual2);
    }

    @Test
    public void shouldReturnRed4_whenOverdueMoreThenThreeDays() {
        LocalDate dayEnd = LocalDate.now().minusDays(4);
        String actual = ControllerKb.applyRowStyleTableView(dayEnd);
        assertEquals("red4", actual);
    }
}
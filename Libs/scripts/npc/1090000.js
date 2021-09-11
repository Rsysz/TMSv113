/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
   @Author Moogra
   Pirate Job Advancement
*/

var status = 0;
var job = 510;
var jobName = "Gunslinger";

function start() {
    if (cm.getPlayer().getJob() == 0) {
        if (cm.getPlayer().getLevel() >= 10)
            cm.sendNext("�A�Q�n����#r���s#k?");
        else {
            cm.sendOk("�p�G�A�Q���� #r���s#k�A�w����.")
            cm.dispose();
        }
    } else {
        if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getJob() == 500) {
			if(cm.getQuestStatus(2191) == 2 || cm.getQuestStatus(2192) == 2){
				cm.sendNext("�A�w�g�����F�ڪ�����");
				status = 9;
			}else if(cm.getQuestStatus(2191) == 1 || cm.getQuestStatus(2192) == 1){
				cm.sendNext("�n�ǳƶ}�l�i����¾���ȶ�?");
				status = 11;
			}else{
				cm.sendOk("�A���������ڪ�����");
				cm.dispose();
			}
		}else if ( (cm.getPlayer().getJob()==510 || cm.getPlayer().getJob()==520) && cm.getPlayer().getLevel() >= 70)
		{
					if(cm.getQuestStatus(100100) == 1) //�T��}�l
					{
						cm.sendOk("�ݰ_�ӧA�Q���j���A�Y�O�n�T�઺�ܡA�Ш�#b�������Ŷ�#k���˧ڪ������A�åB��#b�²�#k�a�^�ӵ���");
						cm.completeQuest(100100);
						cm.startQuest(100101);
						cm.dispose();
					}else if(cm.getQuestStatus(100101) == 1){
						if(cm.haveItem(4031059)){
							if(cm.canHold(4031057)){
								cm.sendOk("�ݨӧA�w�g���˧ڪ������F�A��o#b�O�q����#k�a�^�h�����ѧa!");
								cm.gainItem(4031057,1);
								cm.gainItem(4031059,-1);
								cm.completeQuest(100101);
							}else{
								cm.sendOk("�о�z���~��!");
							}
						}else{
							cm.sendOk("�кɧ֨�#b�������Ŷ�#k���˧ڪ������A�åB��#b�²�#k�a�^�ӵ���!");
						}
						cm.dispose();
					}else if(cm.getQuestStatus(100101) == 2){
						if(!cm.haveItem(4031057)){
							cm.sendOk("��#b�O�q����#k�d��F��?�S���Y�A�ڦA���A");
							cm.gainItem(4031057,1);
						}else{
							cm.sendOk("����h����ѧa!");
						}
						cm.dispose();
					}else{
						cm.sendOk("�A���G�i�H��[�j�j");
						cm.dispose();
					}
		}else if ( (cm.getPlayer().getJob()==512 || cm.getPlayer().getJob()==522) && cm.getPlayer().getLevel() >= 120)
		{
			if(cm.getQuestStatus(6330) == 1) //�ܱj����k
			{
				var dd = cm.getEventManager("KyrinTrainingGroundC");
				if (dd != null) {
					dd.startInstance(cm.getPlayer());
				} else {
					cm.sendOk("�o�ͥ������~�A�гq��GM");
				}
			}else if(cm.getQuestStatus(6370) == 1){
				var dd = cm.getEventManager("KyrinTrainingGroundV");
				if (dd != null) {
					dd.startInstance(cm.getPlayer());
				} else {
					cm.sendOk("�o�ͥ������~�A�гq��GM");
				}
				cm.dispose();
			}else{
				cm.sendOk("�A�D�`���j��");
				cm.dispose();
			}
        } else{
            cm.sendOk("�ڬO�@�W���s���!");
			cm.dispose();
		}
    }
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
        if (status == 1)
            cm.sendNextPrev("��Ӯ��s�O�ݭn���ۥѦۦb���ߡA�B�o�ӿ�ܤ���ᮬ!.");
        else if (status == 2)
            cm.sendYesNo("�A�T�w�n����#r���s#k?");
        else if (status == 3) {
            if (cm.getPlayer().getJob() == 0){
                cm.changeJob(500);
				cm.sendOk("�A�{�b�O�@����s�F!���ܤѤU!");
			}else{
				cm.sendOk("�X���F!");
			}
			cm.dispose();
        }else if( status == 10 ){
			if(cm.getQuestStatus(2191) == 2 ){
				cm.sendYesNo("�A�T�w�n����#r����#k?");
				status = 10;
			}else if(cm.getQuestStatus(2192) == 2 ){
				cm.sendYesNo("�A�T�w�n����#r�j��#k?");
				status = 10;
			}else{
				cm.sendOk("�X���F!");
				cm.dispose();
			}
		}else if(status == 11 ){
			if(cm.getQuestStatus(2191) == 2 && cm.getPlayer().getJob() == 500 ){
				cm.changeJob(510);
				cm.sendOk("�A�{�b�@�W#r����#k�F");
				cm.dispose();
			}else if(cm.getQuestStatus(2192) == 2 && cm.getPlayer().getJob() == 500 ){
				cm.changeJob(520);
				cm.sendOk("�A�{�b�@�W#r�j��#k�F");
				cm.dispose();
			}
		}else if(status == 12){
			if(cm.getQuestStatus(2191) == 1)
				cm.warp(108000501);
			else
				cm.warp(108000500);
			cm.dispose();
		}
    } else if (mode == 0) {
        cm.sendOk("�p�G�A���ܷQ�k�A�H���w��A�^��");
        cm.dispose();
    }  else {
        cm.dispose();
    }
}
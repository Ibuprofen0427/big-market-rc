package com.rc.domain.strategy.service.rule.tree.factory;

import java.util.*;

class ListNode{
    int val;
    ListNode next;
    ListNode(int val){
        this.val = val;
    }

}

public class Main {
    public static void main(String[] args) {
        // Scanner in=new Scanner(System.in);
        ListNode l1=new ListNode(1);
        l1.next=new ListNode(4);
        l1.next.next=new ListNode(5);
        ListNode l2=new ListNode(1);
        l2.next=new ListNode(3);
        l2.next.next=new ListNode(4);
        ListNode l3=new ListNode(2);
        l3.next=new ListNode(6);
        ListNode[] lists=new ListNode[]{l1,l2,l3};
        ListNode resNode=mergeK(lists);
        // 输出
//        while(resNode!=null){
//            System.out.print(resNode.val+ " ");
//            resNode=resNode.next;
//        }
        printList(resNode);

    }

    public static ListNode mergeK(ListNode[] lists){
        if(lists==null || lists.length==0){
            return null;
        }
        PriorityQueue<ListNode> pq=new PriorityQueue<>((a,b) -> a.val-b.val);
        for(ListNode node:lists){
            if(node!=null){
                pq.offer(node);
            }
        }
        ListNode dummy=new ListNode(0);
        ListNode current=dummy;

        while(!pq.isEmpty()){
            ListNode tmpNode=pq.poll();
            current.next=tmpNode;
            current=current.next;
            if(tmpNode.next!=null){
                pq.offer(tmpNode.next);
            }
        }
        return dummy.next;
    }
     public static void printList(ListNode node){
         while(node!=null){
             System.out.print(node.val+" ");
             node=node.next;
         }
//         System.out.println();
     }
}
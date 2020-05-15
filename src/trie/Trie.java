package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		Indexes f=null;
		TrieNode tree=new TrieNode(f,null,null);
		String temp="";
		if (allWords.length==0)
		{
			return null;
		}
		for (int i=0; i<allWords.length;i++)
		{
			if (i==0)
			{
				temp=allWords[i];
				Indexes c=new Indexes(0,(short)0,(short)0);
				c.wordIndex=i;
				c.startIndex=(short)i;
				c.endIndex=(short)(temp.length()-1);
				TrieNode tree2=new TrieNode(c,null,null);
				tree.firstChild=tree2;
			}
			else
			{
				tree=insert(tree,allWords,allWords[i],i);
			}
		}
		return tree;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		TrieNode change=root;
		ArrayList<TrieNode>p=new ArrayList<TrieNode>();
		if(allWords.length==0)
		{
			return null;
		}
		p=traversal(change.firstChild,allWords,prefix,p);
		if (p.size()==0)
		{
			return null;
		}
		return p;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
private static TrieNode insert(TrieNode r,String[] allWords, String word, int index )
{
	TrieNode change=r;
	TrieNode root=r;
	TrieNode prev=r;
	TrieNode prev2=r;
	change=change.firstChild;
	int count=0;
	int findword=0;
	int tempnumber1=0;
	int tempnumber2=0;
	int check=-1;
	while(true)
	{
		findword=change.substr.wordIndex;
		String compword=allWords[findword];
		for (int i=0; i<compword.length();i++)
		{
			if (word.substring(i, i+1).equals (compword.substring(i, i+1)))
			{
				tempnumber2=i;
				count++;
				if(i+1==word.length()|| i+1==compword.length())
				{
					break;
				}
			}
			else
			{
				break;
			}
		}
		if(count==0 && change.sibling !=null)
		{
			prev=change;
			change=change.sibling;
		}
		else if(count==0 && change.sibling==null)
		{
			Indexes new1=new Indexes(0,(short)0,(short)0);
			new1.wordIndex=index;
			new1.startIndex=(short)0;
			new1.endIndex=(short)(word.length()-1);
			TrieNode tree=new TrieNode(new1,null,null);
			change.sibling=tree;
			break;
		}
		else if(count !=0 && change.firstChild==null && count>check )
		{
			Indexes new1=new Indexes(0,(short)0,(short)0);
			new1.wordIndex=change.substr.wordIndex;
			new1.startIndex=(short)(tempnumber2+1);
			new1.endIndex=(short)(change.substr.endIndex);
			TrieNode tree=new TrieNode(new1,null,null);
			if (change.substr.startIndex>tempnumber2 && change.sibling !=null)
			{
				prev=change;
				change=change.sibling;
				count=0;
			}
			else if(change.substr.startIndex>tempnumber2 && change.sibling ==null)
			{
				Indexes new4=new Indexes(0,(short)0,(short)0);
				new4.wordIndex=index;
				new4.startIndex=(short)(change.substr.startIndex);
				new4.endIndex=(short)(word.length()-1);
				TrieNode tree4=new TrieNode(new4,null,null);
				change.sibling=tree4;
				break;
			}
			else
			{
				change.substr.endIndex=(short)tempnumber2;
				change.substr.startIndex=(short)(change.substr.startIndex);
				change.firstChild=tree;
				Indexes new2=new Indexes(0,(short)0,(short)0);
				new2.wordIndex=index;
				new2.startIndex=(short)(tempnumber2+1);
				new2.endIndex=(short)(allWords[index].length()-1);
				TrieNode tree2=new TrieNode(new2,null,null);
				tree.sibling=tree2;
				break;
			}
		}
		else if(count !=0 && change.firstChild!=null)
		{
			check=change.substr.endIndex+1;
			if (count<check  && (prev2==root|| count-1!=prev2.substr.endIndex) )
			{	
				Indexes new1=new Indexes(0,(short)0,(short)0);
				new1.wordIndex=change.substr.wordIndex;
				if (prev2==root)
				{
					new1.startIndex=(short)(0);
				}
				else
				{
					new1.startIndex=(short)(prev2.substr.endIndex+1);
				}
				new1.endIndex=(short)(count-1);
				TrieNode tree=new TrieNode(new1,change,change.sibling);
				change.sibling=null;
				change.substr.startIndex=(short)count;
				if (prev==root || prev.firstChild==change)
				{
					prev.firstChild=tree;
				}
				else if(prev.sibling==change)
				{
					prev.sibling=tree;
				}
				Indexes new2=new Indexes(0,(short)0,(short)0);
				new2.wordIndex=index;
				new2.startIndex=(short)(tempnumber2+1);
				new2.endIndex=(short)(word.length()-1);
				TrieNode tree2=new TrieNode(new2,null,null);
				change.sibling=tree2;
				break;
			}
			if(allWords[change.substr.wordIndex].substring(0,change.substr.endIndex+1).length()>word.length())
			{	
				if(allWords[change.substr.wordIndex].substring(0,change.substr.endIndex+1).length()>word.length()&& change.sibling==null)
				{
					Indexes new2=new Indexes(0,(short)0,(short)0);
					new2.wordIndex=index;
					new2.startIndex=(short)(tempnumber2+1);
					new2.endIndex=(short)(word.length()-1);
					TrieNode tree2=new TrieNode(new2,null,null);
					change.sibling=tree2;
					break;
				}
				else if(allWords[change.substr.wordIndex].substring(0,change.substr.endIndex+1).length()>word.length()&& change.sibling!=null)
				{
					prev=change;
					change=change.sibling;
					count=0;
					check=0;
				}
				else
				{	
					tempnumber1+=change.substr.endIndex-change.substr.startIndex+1;
					prev2=change;
					prev=change;
					change=change.firstChild;
					count=0;
				}
			}
			else 
			{
				if((!(word.substring(change.substr.startIndex,change.substr.endIndex+1)).equals( allWords[change.substr.wordIndex].substring(change.substr.startIndex,change.substr.endIndex+1))&& change.sibling==null))
				{
					Indexes new2=new Indexes(0,(short)0,(short)0);
					new2.wordIndex=index;
					new2.startIndex=(short)(tempnumber2+1);
					new2.endIndex=(short)(word.length()-1);
					TrieNode tree2=new TrieNode(new2,null,null);
					change.sibling=tree2;
					break;
				}
				else if((!(word.substring(change.substr.startIndex,change.substr.endIndex+1)).equals( allWords[change.substr.wordIndex].substring(change.substr.startIndex,change.substr.endIndex+1))&& change.sibling!=null))
				{
					prev=change;
					change=change.sibling;
					count=0;
					check=0;
				}
				else
				{	
					tempnumber1+=change.substr.endIndex-change.substr.startIndex+1;
					prev2=change;
					prev=change;
					change=change.firstChild;
					count=0;
				}
			}
		}
		else if(count !=0 && change.firstChild==null && count==check && change.sibling !=null)
		{
			prev=change;
			change=change.sibling;
			count=0;
		}
		else 
		{
			Indexes new1=new Indexes(0,(short)0,(short)0);
			new1.wordIndex=index;
			new1.startIndex=(short)(tempnumber2+1);
			new1.endIndex=(short)(word.length()-1);
			TrieNode tree=new TrieNode(new1,null,null);
			change.sibling=tree;
			break;
		}
	}
	return root;	
}
private static ArrayList<TrieNode> traversal(TrieNode root, String[] allWords, String prefix,ArrayList<TrieNode>j)
{
	if (root==null )
	{
		return null;
	}
	int a=0;
	int b=0;
	int c=prefix.length();
	if(root.substr.startIndex==0)
	{
		b=root.substr.endIndex-root.substr.startIndex+1;
	}
	else
	{
		b=root.substr.endIndex+1;
	}
	System.out.println(root.substr.wordIndex);
	System.out.println(root.substr.startIndex);
	System.out.println(root.substr.endIndex);
	if (b>=c)
	{
		if((!allWords[root.substr.wordIndex].substring(0,prefix.length()).equals(prefix))&& root.sibling !=null )
		{
			root=root.sibling;
			a=1;
		}
		if (a==0 && !allWords[root.substr.wordIndex].substring(0,prefix.length()).equals(prefix)&& root.sibling ==null)
		{
			return j;
		}
		if (a==0 && allWords[root.substr.wordIndex].substring(0,prefix.length()).equals(prefix) && root.firstChild ==null)
		{
			j.add(root);
		}
	}
	if (b<c)
	{
		if ((!allWords[root.substr.wordIndex].substring(0,root.substr.endIndex+1).equals(prefix.substring(0,root.substr.endIndex+1)))&& root.sibling !=null)
		{
			root=root.sibling;
			a=1;
		}
		if (a==0 &&!allWords[root.substr.wordIndex].substring(0,root.substr.endIndex+1).equals(prefix.substring(0,root.substr.endIndex+1))&& root.sibling ==null )
		{
			return j;
		}
	}
	if (a==1)
	{
		traversal(root,allWords,prefix,j);
	}
	else
	{
		traversal(root.firstChild,allWords,prefix,j);
		traversal(root.sibling,allWords,prefix,j);
	}
	return j;
}
}
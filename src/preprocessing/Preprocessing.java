package preprocessing;

import java.util.ArrayList;
import util.FileOperations;
import util.Post;
import util.User;

public class Preprocessing {

    private final ArrayList<User>[] users;
    //private static final String STOPWORDS = "a about above after again against all am an and any are aren't as at be because been before being below between both but by can't cannot could couldn't did didn't do does doesn't doing don't down during each few for from further had hadn't has hasn't have haven't having he he'd he'll he's her here here's hers herself him himself his how how's i i'd i'll i'm i've if in into is isn't it it's its itself let's me more most mustn't my myself no nor not of off on once only or other ought our ours ourselves out over own same shan't she she'd she'll she's should shouldn't so some such than that that's the their theirs them themselves then there there's these they they'd they'll they're they've this those through to too under until up very was wasn't we we'd we'll we're we've were weren't what what's when when's where where's which while who who's whom why why's with won't would wouldn't you you'd you'll you're you've your yours yourself yourselves";
    private static final ArrayList<String> STOPWORDS = new ArrayList<>();

    static {
        STOPWORDS.add("a");
        STOPWORDS.add("about");
        STOPWORDS.add("above");
        STOPWORDS.add("after");
        STOPWORDS.add("again");
        STOPWORDS.add("against");
        STOPWORDS.add("all");
        STOPWORDS.add("am");
        STOPWORDS.add("an");
        STOPWORDS.add("and");
        STOPWORDS.add("any");
        STOPWORDS.add("are");
        STOPWORDS.add("aren't");
        STOPWORDS.add("as");
        STOPWORDS.add("at");
        STOPWORDS.add("be");
        STOPWORDS.add("because");
        STOPWORDS.add("been");
        STOPWORDS.add("before");
        STOPWORDS.add("being");
        STOPWORDS.add("below");
        STOPWORDS.add("between");
        STOPWORDS.add("both");
        STOPWORDS.add("but");
        STOPWORDS.add("by");
        STOPWORDS.add("can't");
        STOPWORDS.add("cannot");
        STOPWORDS.add("could");
        STOPWORDS.add("couldn't");
        STOPWORDS.add("did");
        STOPWORDS.add("didn't");
        STOPWORDS.add("do");
        STOPWORDS.add("does");
        STOPWORDS.add("doesn't");
        STOPWORDS.add("doing");
        STOPWORDS.add("don't");
        STOPWORDS.add("down");
        STOPWORDS.add("during");
        STOPWORDS.add("each");
        STOPWORDS.add("few");
        STOPWORDS.add("for");
        STOPWORDS.add("from");
        STOPWORDS.add("further");
        STOPWORDS.add("had");
        STOPWORDS.add("hadn't");
        STOPWORDS.add("has");
        STOPWORDS.add("hasn't");
        STOPWORDS.add("have");
        STOPWORDS.add("haven't");
        STOPWORDS.add("having");
        STOPWORDS.add("he");
        STOPWORDS.add("he'd");
        STOPWORDS.add("he'll");
        STOPWORDS.add("he's");
        STOPWORDS.add("her");
        STOPWORDS.add("here");
        STOPWORDS.add("here's");
        STOPWORDS.add("hers");
        STOPWORDS.add("herself");
        STOPWORDS.add("him");
        STOPWORDS.add("himself");
        STOPWORDS.add("his");
        STOPWORDS.add("how");
        STOPWORDS.add("how's");
        STOPWORDS.add("i");
        STOPWORDS.add("i'd");
        STOPWORDS.add("i'll");
        STOPWORDS.add("i'm");
        STOPWORDS.add("i've");
        STOPWORDS.add("if");
        STOPWORDS.add("in");
        STOPWORDS.add("into");
        STOPWORDS.add("is");
        STOPWORDS.add("isn't");
        STOPWORDS.add("it");
        STOPWORDS.add("it's");
        STOPWORDS.add("its");
        STOPWORDS.add("itself");
        STOPWORDS.add("let's");
        STOPWORDS.add("me");
        STOPWORDS.add("more");
        STOPWORDS.add("most");
        STOPWORDS.add("mustn't");
        STOPWORDS.add("my");
        STOPWORDS.add("myself");
        STOPWORDS.add("no");
        STOPWORDS.add("nor");
        STOPWORDS.add("not");
        STOPWORDS.add("of");
        STOPWORDS.add("off");
        STOPWORDS.add("on");
        STOPWORDS.add("once");
        STOPWORDS.add("only");
        STOPWORDS.add("or");
        STOPWORDS.add("other");
        STOPWORDS.add("ought");
        STOPWORDS.add("our");
        STOPWORDS.add("ours");
        STOPWORDS.add("ourselves");
        STOPWORDS.add("out");
        STOPWORDS.add("over");
        STOPWORDS.add("own");
        STOPWORDS.add("same");
        STOPWORDS.add("shan't");
        STOPWORDS.add("she");
        STOPWORDS.add("she'd");
        STOPWORDS.add("she'll");
        STOPWORDS.add("she's");
        STOPWORDS.add("should");
        STOPWORDS.add("shouldn't");
        STOPWORDS.add("so");
        STOPWORDS.add("some");
        STOPWORDS.add("such");
        STOPWORDS.add("than");
        STOPWORDS.add("that");
        STOPWORDS.add("that's");
        STOPWORDS.add("the");
        STOPWORDS.add("their");
        STOPWORDS.add("theirs");
        STOPWORDS.add("them");
        STOPWORDS.add("themselves");
        STOPWORDS.add("then");
        STOPWORDS.add("there");
        STOPWORDS.add("there's");
        STOPWORDS.add("these");
        STOPWORDS.add("they");
        STOPWORDS.add("they'd");
        STOPWORDS.add("they'll");
        STOPWORDS.add("they're");
        STOPWORDS.add("they've");
        STOPWORDS.add("this");
        STOPWORDS.add("those");
        STOPWORDS.add("through");
        STOPWORDS.add("to");
        STOPWORDS.add("too");
        STOPWORDS.add("under");
        STOPWORDS.add("until");
        STOPWORDS.add("up");
        STOPWORDS.add("very");
        STOPWORDS.add("was");
        STOPWORDS.add("wasn't");
        STOPWORDS.add("we");
        STOPWORDS.add("we'd");
        STOPWORDS.add("we'll");
        STOPWORDS.add("we're");
        STOPWORDS.add("we've");
        STOPWORDS.add("were");
        STOPWORDS.add("weren't");
        STOPWORDS.add("what");
        STOPWORDS.add("what's");
        STOPWORDS.add("when");
        STOPWORDS.add("when's");
        STOPWORDS.add("where");
        STOPWORDS.add("where's");
        STOPWORDS.add("which");
        STOPWORDS.add("while");
        STOPWORDS.add("who");
        STOPWORDS.add("who's");
        STOPWORDS.add("whom");
        STOPWORDS.add("why");
        STOPWORDS.add("why's");
        STOPWORDS.add("with");
        STOPWORDS.add("won't");
        STOPWORDS.add("would");
        STOPWORDS.add("wouldn't");
        STOPWORDS.add("you");
        STOPWORDS.add("you'd");
        STOPWORDS.add("you'll");
        STOPWORDS.add("you're");
        STOPWORDS.add("you've");
        STOPWORDS.add("your");
        STOPWORDS.add("yours");
        STOPWORDS.add("yourself");
        STOPWORDS.add("yourselves");
    }

    ; 
    
    public Preprocessing(ArrayList<User>[] users) {
        this.users = users;
    }

    public void run() {
        run(users[User.BOTS]);
        run(users[User.HUMANS]);
    }

    private void run(ArrayList<User> users) {
        for (User u : users) {
            ArrayList<Post> posts = u.getPosts();
            ArrayList<Post> processedPosts = new ArrayList<>();
            for (Post p : posts) {
                String newTweet = new String();
                for (String word : p.getTweet().split("\\s+")) {
                    if (!STOPWORDS.contains(word.toLowerCase())) {
                        newTweet += word + " ";
                    }
                }
                Post newPost = p;
                newPost.setTweet(newTweet);
                processedPosts.add(newPost);
            }
            u.setProcessedPosts(processedPosts);
        }
    }

    public void extractFiles() {
        FileOperations.writeUsers(FileOperations.PROCESSED_BOTS_PATH, users[User.BOTS]);
        FileOperations.writeUsers(FileOperations.PROCESSED_HUMANS_PATH, users[User.HUMANS]);
    }
}

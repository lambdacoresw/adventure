import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Item implements Inspectable, Visible {

	public Item(String d, String sd, String[] a) {
		this.description = d;
		this.detailDescription = sd;
		this.aliases = a;
		this.relatedRoom = null;
		this.relatedItem = null;
		this.inspectMessage = null;
	}
	private static void initSharedInstances() {
		
		sharedInstances = new LinkedList<Item>();
		sharedInstances.add(new ItemShovel("shovel", "metal shovel",  new String[]{"shovel"}));
		sharedInstances.add(new ItemBrick   ("brick",  "clay brick",    new String[]{"brick"}));
		sharedInstances.add(new ItemFood    ("food",   "food",          new String[]{"food"}));
		sharedInstances.add(new ItemLadder  ("ladder", "wooden ladder", new String[]{"ladder"}));
		sharedInstances.add(new ItemKey ("key", "gold key", new String[]{"key"}));
		sharedInstances.add(new ItemLock  ("lock", "gold lock", new String[]{"lock"}));
		sharedInstances.add(new ItemKeycard ("keycard", "plastic keycard", new String[]{"keycard", "card"}));
		sharedInstances.add(new ItemKeycardReader ("keycard reader", "metal keycard reader", new String[]{"reader", "slot"}));
		sharedInstances.add(new ItemClayPot ("pot",    "clay pot", 	new String[]{"pot", "pottery"}));
		sharedInstances.add(new ItemDiamond ("diamond", "white diamond", new String[]{"diamond", "jewel"}));
		sharedInstances.add(new ItemGold    ("gold", "shiny gold bar", new String[]{"gold", "bar"}));
		sharedInstances.add(new ItemRMS("statue", "wax statuette of Richard M Stallman", new String[]{"statue", "statuette", "rms"}));
		sharedInstances.add(new ItemMicrowave ("microwave", "microwave that stinks of month old popcorn", new String[]{"microwave", "appliance"}));
		sharedInstances.add(new ItemFridge ("fridge", "white refrigerator", new String[]{"fridge", "refrigerator"}));
		sharedInstances.add(new ItemFlashlight ("flashlight", "battery operated flashlight", new String[]{"flashlight", "light"}));
		sharedInstances.add(new ItemGuard ("guard", "sleeping guard", new String[]{"guard", "henchman"}));
		sharedInstances.add(new ItemParachute ("parachute", "packed parachute", new String[]{"chute", "parachute"}));
		sharedInstances.add(new ItemWatch ("watch", "smart watch", new String[]{"watch"}));
		sharedInstances.add(new ItemGhillieSuit("camouflage", "Ghillie Suit", new String[]{"suit", "disguise", "ghillie", "camo", "camouflage"}));
		sharedInstances.add(new ItemJunctionBox("box", "junction box", new String[]{"box", "junction", "meter", "electric", "electricity", "power"}));
		sharedInstances.add(new ItemMagicBox("pit", "bottomless pit", new String[]{"pit", "hole"}));
		sharedInstances.add(new ItemVendingMachine("machine", "vending machine with assorted candies and treats", new String[]{"machine", "vendor"}));
		sharedInstances.add(new ItemSafe("safe", "bullet-proof safe", new String[]{"safe"}));
		sharedInstances.add(new ItemFolder("folder", "manilla folder", new String[]{"folder"}));
		sharedInstances.add(new ItemDocument("dossier", "top secret dossier on Amrid Al-Asad", new String[]{"document", "dossier"}));
		sharedInstances.add(new ItemGrate("grate", "metal grate", new String[]{"grate", "vent"}));

		sharedInstances.add(new ItemButton ("Button", "Elevator Button", new String[]{"button"}));
		sharedInstances.add(new ItemButton ("Floor 1 Button", "Elevator Floor 1 Button", new String[]{"1"}));
		sharedInstances.add(new ItemButton ("Floor 2 Button", "Elevator Floor 2 Button", new String[]{"2"}));
		sharedInstances.add(new ItemButton ("Floor 3 Button", "Elevator Floor 3 Button", new String[]{"3"}));
		sharedInstances.add(new ItemButton ("Floor 4 Button", "Elevator Floor 4 Button", new String[]{"4"}));

		sharedInstances.add(new ItemUnknown ("unknown", "unknown", new String[]{"unknown"}));

		checkUniqueAliases();
	}
	public static Item getInstance(String s) {
		if(sharedInstances == null) {
			initSharedInstances();
		}
		for(Item i : sharedInstances) {
			for(String a : i.getAliases()) {
				if(s.equals(a)) {
					return i;
				}
			}
		}
		return null;
	}
	private static void checkUniqueAliases() {
		for(Item item : sharedInstances) {
			for(Item i : sharedInstances) {
				if(item == i) {
					continue;
				}
				for(String string : item.getAliases()) {
					for(String s : i.getAliases()) {
						if(string.equals(s)) {
							System.err.println("Warning: alias conflict between " + item + " and " + i);
						}
					}
				}
			}
		}
	}
	public Item relatedItem() {
		return this.relatedItem;
	}	
	public void setRelatedItem(Item i) {
		this.relatedItem = i;
	}
	public Room relatedRoom() {
		return this.relatedRoom;
	}	
	public void setRelatedRoom(Room r) {
		this.relatedRoom = r;
	}
	public String[] getAliases() {
		return this.aliases;
	}
	public String toString() {
		return this.description;
	}
	public String detailDescription() {
		return this.detailDescription;
	}
	public void setDescription(String s) {
		this.description = s;
	}
	public void setDetailDescription(String s) {
		this.detailDescription = s;
	}
// Visible
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean b) {
		this.visible = b;
	}
// Inspectable
 	public void inspect() {
		if(this.inspectMessage != null) {
			Game.print(this.inspectMessage);
		}
		else {
			Game.print("It appears to be a " + this + ".");
		}
 	}
	public void setInspectMessage(String message) {
		this.inspectMessage = message;
	}
	protected boolean visible = true;
	protected String description;
	protected String detailDescription;
	protected String[] aliases;
	protected static LinkedList<Item> sharedInstances;
	protected Room relatedRoom; // items can open rooms, call elevators, etc (e.g., an ItemButton instance)
	protected Item relatedItem; // items can also affect other items, like setting other items breakable (like a junction box);
	protected String inspectMessage;
}

class ItemBrick extends Item implements Holdable{

	public ItemBrick(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemButton extends Item implements Pushable {

	public ItemButton(String s, String sd, String[] a) {
		super(s, sd, a);
	}
	public void push() {

	}
}
class ItemClayPot extends Item implements Destroyable, Holdable, Hostable {

	public ItemClayPot(String s, String sd, String[] a) {
		super(s, sd, a);
		this.installedItem = null;
	}
	public void setDestroyMessage(String s) {
		this.destroyMessage = s;
	}
	public void destroy() {
		Game.print(destroyMessage);
	}
	public void setDisappears(boolean b) {
		this.disappears = b;
	}
	public boolean disappears() {
		return this.disappears;
	}
	public void install(Item i) {
		this.installedItem = i;
	}
	public boolean uninstall(Item i) {
		if(this.installedItem == null) {
			return false;
		}
		else if(this.installedItem == i) {
			this.installedItem = null;
			return true;
		}
		else {
			return false;
		}
	}
	public Item installedItem() {
		return this.installedItem;
	}
	protected String destroyMessage;
	protected Item installedItem;
	protected boolean disappears;
}
class ItemDiamond extends Item implements Holdable, Installable, Valuable {

	public ItemDiamond(String s, String sd, String[] a) {
		super(s, sd, a);
	}
	public int value() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	protected int value;
}
class ItemDocument extends Item implements Holdable, Installable {

	public ItemDocument(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemFlashlight extends Item implements Holdable, Installable {

	public ItemFlashlight(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemFolder extends Item {

	public ItemFolder(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemFood extends Item implements Edible, Holdable {

	public ItemFood(String s, String sd, String[] a) {
		super(s, sd, a);
	}
	public void eat() {
		Game.print("Yummy");
	}		
}
class ItemFridge extends Item implements Pushable{

	public ItemFridge(String s, String sd, String[] a) {
		super(s, sd, a);
		this.wasPushed = false;
	}
// Pushable
	public void push() {
		if(!this.wasPushed) {
			if(this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
				((RoomObscured)this.relatedRoom).setObscured(false);
				Game.print(((RoomObscured)this.relatedRoom).obscureMessage());
			}
			this.wasPushed = true;
		}
	}
	protected boolean wasPushed;
}
class ItemGrate extends Item implements Destroyable {

	public ItemGrate(String s, String sd, String[] a) {
		super(s, sd, a);
		this.destroyMessage = null;
	}
	public void destroy() {
		// set obscured room unobscured
		if(this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
			((RoomObscured)this.relatedRoom).setObscured(false);
		}
	}
	public boolean disappears() { 
		return false;
	}
	public void setDestroyMessage(String s) {
		this.destroyMessage = s;
	}
	public void setDisappears(boolean b) {
		this.disappears = b;
	}
	protected boolean disappears;
	protected String destroyMessage;
}
class ItemKey extends Item implements Holdable, Installable {

	public ItemKey(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemGhillieSuit extends Item implements Wearable {

	public ItemGhillieSuit(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemGold extends Item implements Installable, Holdable, Valuable {

	public ItemGold(String s, String sd, String[] a) {
		super(s, sd, a);
		this.value = 0;
	}
	
	public int value() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	protected int value;
}
class ItemGuard extends Item implements Hostable, Killable {

	public ItemGuard(String s, String sd, String[] a) {
		super(s, sd, a);
		this.deathMessage = null;
	}
	public void kill() {
		if(!this.isDead) {
			Game.print(this.deathMessage);
			Game.print("It looks like he dropped something");
			this.detailDescription = "dead guard";
			this.relatedRoom.putItem(this.installedItem);
			this.isDead = true;
		}
		else {
			Game.print("The guard has already perished.");
		}
	}
	public void install(Item i) {
		if(this.canInstall) {
			this.installedItem = i;
			this.canInstall = false;  // now the player cannot put the card back "in" the guard
		}
	}
	public boolean uninstall(Item i) {
		if(this.installedItem == null) {
			return false;
		}
		else if(this.installedItem == i) {
			this.installedItem = null;
			return true;
		}
		else {
			return false;
		}
	}
	public Item installedItem() {
		return this.installedItem;
	}
	public String deathMessage() {
		return this.deathMessage;
	}	
	public void setDeathMessage(String s) {
		this.deathMessage = s;
	}
	protected Item installedItem;
	protected boolean disappears;
	protected boolean canInstall = true;
	protected boolean isDead = false;
	protected String deathMessage;
}
class ItemJunctionBox extends Item implements Destroyable {

	public ItemJunctionBox(String s, String sd, String[] a) {
		super(s, sd, a);
	}
	public void destroy() {
		if(this.canBeDestroyed) {
			Game.print(this.destroyMessage);
			this.canBeDestroyed = false;
			((RoomDark)this.relatedRoom).setDark(false);
		}
		else {
			Game.print("You have already short circuited this junction box.");
		}
	}
	public boolean disappears() { 
		return false;
	}
	public void setDestroyMessage(String s) {
		this.destroyMessage = s;
	}
	public void setDisappears(boolean b) {
		this.disappears = b;
	}
	protected boolean canBeDestroyed = true;
	protected boolean disappears;
	protected String destroyMessage;
}
class ItemKey extends Item implements Holdable, Installable {

	public ItemKey(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemKeycard extends Item implements Holdable, Installable {

	public ItemKeycard(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemKeycardReader extends Item implements Hostable {

	public ItemKeycardReader(String s, String sd, String[] a) {
		super(s, sd, a);
		this.installMessage = null;
	}
	public void install(Item item) {
		this.installedItem = item;

		for(int i=0; i < 3; i++) {
			Game.print("...");
			try {
				Thread.sleep(1000);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		if(this.installMessage != null) {
			Game.print(this.installMessage);
		}
		((Visible)this.relatedItem).setVisible(true);
	}
	public boolean uninstall(Item i) {
		if(this.installedItem == null) {
			return false;
		}
		else if(this.installedItem == i) {
			this.installedItem = null;
			return true;
		}
		else {
			return false;
		}
	}
	public Item installedItem() {
		return this.installedItem;
	}
	public void setInstallMessage(String s) {
		this.installMessage = s;
	}
	protected Item installedItem;
	protected String installMessage;
}
class ItemLadder extends Item implements Holdable {

	public ItemLadder(String s, String sd, String[] a) {
		super(s, sd, a);
	} 
}
class ItemLock extends Item implements Hostable {

	public ItemLock(String s, String sd, String[] a) {
		super(s, sd, a);
	}
	public void install(Item i) {
		this.installedItem = i;

		if(this.relatedRoom != null && this.relatedRoom instanceof RoomLockable) {
			((RoomLockable)this.relatedRoom).unlock(this.installedItem);
		}
	}
	public boolean uninstall(Item i) {
		if(this.installedItem == null) {
			return false;
		}
		else if(this.installedItem == i) {
			this.installedItem = null;
			return true;
		}
		else {
			return false;
		}
	}
	public Item installedItem() {
		return this.installedItem;
	}
	protected Item installedItem;
	protected boolean disappears;
}
class ItemMagicBox extends Item implements Hostable {

	public ItemMagicBox(String s, String sd, String[] a) {
		super(s, sd, a);
	}

	public void install(Item i) {
		//this.installedItem = i;
		// items fall into black hole
	}
	public boolean uninstall(Item i) {
		return false;
	}
	public Item installedItem() {
		return null;
	}
}
class ItemMicrowave extends Item implements Hostable, Startable {

	public ItemMicrowave(String s, String sd, String[] a) {
		super(s, sd, a);
		this.installedItem = null;
	}

	public void start() {
		
		for(int i=0; i < 3; i++) {
			Game.print("...");
			try {
				Thread.sleep(1000);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		Game.print("Beep beep beep");
		if(this.installedItem instanceof Meltable) {
			Item item = ((Meltable)this.installedItem).meltItem();
			Game.print("You melted the " + this.installedItem.detailDescription() + ", and it revealed a " + item.detailDescription() + "!");
			this.installedItem = item;
		}
	}
	public void install(Item i) {
		this.installedItem = i;
	}
	public boolean uninstall(Item i) {
		if(this.installedItem == null) {
			return false;
		}
		else if(this.installedItem == i) {
			this.installedItem = null;
			return true;
		}
		else {
			return false;
		}
	}
	public Item installedItem() {
		return this.installedItem;
	}
	protected Item installedItem;
}
class ItemParachute extends Item implements Startable, Wearable {

	public ItemParachute(String s, String sd, String[] a) {
		super(s, sd, a);
	}
	public void start() {
		((RoomSky)this.relatedRoom).breakFall();
	}
}
class ItemRMS extends Item implements Installable, Holdable, Meltable {

	public ItemRMS(String s, String sd, String[] a) {
		super(s, sd, a);
		this.meltItem = null;
	}
	public void setMeltItem(Item item) {
		this.meltItem = item;
	}	
	public Item meltItem() {
		return this.meltItem;
	}
	protected Item meltItem;
}
class ItemSafe extends Item implements Hostable, Openable {
	
	public ItemSafe(String d, String sd, String[] a) {
		super(d, sd, a);
		this.installedItem = null;
		this.pin = null;
	}
	public void setPIN(String pin) {
		this.pin = pin;
	}
// Hostable
	public void install(Item i) {
		this.installedItem = i;
	}
	public boolean uninstall(Item i) {
		if(this.installedItem == null) {
			return false;
		}
		else if(this.installedItem == i) {
			this.installedItem = null;
			return true;
		}
		else {
			return false;
		}
	}
	public Item installedItem() {
		return this.installedItem;
	}
// Openable
	public void open() {
		Scanner s = new Scanner(System.in);
		String input = "";
		Game.print("Enter the four-digit PIN number.");
		input = s.nextLine();
		if(input.equals(this.pin)) {
			this.installedItem.setVisible(true);
			Game.print("The safe door swings open.");
			if(this.installedItem != null) {
				Game.print("You have revealed a " + this.installedItem.detailDescription() + ".");
			}
		}
		else {
			Game.print("Incorrect PIN.");
		}
	}
	protected Item installedItem;
	protected String pin;
}
class ItemShovel extends Item implements Holdable {
	
	public ItemShovel(String d, String sd, String[] a) {
		super(d, sd, a);
	}
}
class ItemUnknown extends Item {

	public ItemUnknown(String s, String sd, String[] a) {
		super(s, sd, a);
	}
}
class ItemVendingMachine extends Item implements Shakeable {

	public ItemVendingMachine(String d, String sd, String[] a) {
		super(d, sd, a);
		this.count = 0;
	}
	public boolean deadly() {
		if(this.count > 2) {
			return true;
		}
		else {
			return false;
		}
	}
	public void shake() {
		switch(this.count) {
			case 0:
				Game.print("You shake the vending machine, and your favorite treat inches its way off the tray.");
				break;
			case 1:
				Game.print("The treat begins to bend toward the will of gravity.");
				break;
			case 2:
				Game.print("Just as the candy falls, the machine also falls over and crushes your body.");
				break;
			default:
				break;
		}
		this.count++;
	}
	protected int count;
}
class ItemWatch extends Item implements Holdable {
	
	public ItemWatch(String d, String sd, String[] a) {
		super(d, sd, a);
		this.stack = new Stack<ItemWatchMenu>();
	}
// Inspectable
	public void inspect() {

		Scanner s = new Scanner(System.in);
		String input = "";
		Game.print("\nCIA SmartWatch v3.2.2\n");
		sleep(800);
		Game.print("Authenticating via retina scan...");
		for(int i=0; i < 3; i++) { 
			sleep(800);
			Game.print("...");
		}
		sleep(800);
		Game.print("Retina scan complete. A hologram appears.");
		sleep(800);
		int n = 0;
		out:
		while(true) {
			ItemWatchMenu menu = this.stack.peek();
			System.out.println(menu.toString());
			while(true) {
				System.out.print("$ ");
				input = s.nextLine();
				try {
					n = Integer.parseInt(input);
				}
				catch(Exception e) {
					if(input.toLowerCase().equals("exit")) {
						while(this.stack.size() > 1) stack.pop();
						break out;
					}
					Game.print("Invalid selection.");
					continue;
				}
				if(n == menu.count()) {
					if(this.stack.size() == 1) {
						break out;
					}
					else {
						this.stack.pop();
					}
				}
				else if (n > menu.count() || n < 0) {
					Game.print("Invalid selection.");
					continue;
				}
				else {
					this.stack.push(menu.get(n-1));
				}
				break;
			}
		}
		Game.print("You turn off the watch and lower your wrist.");
	}
	public void setMenu(ItemWatchMenu main) {
		this.stack.push(main);
	}
	private void sleep(int s) {
		try{
			Thread.sleep(s);
		}
		catch(Exception e1) {
			// pass
		}
	}
	protected Stack<ItemWatchMenu> stack;
}

class ItemWatchMenu {

	public ItemWatchMenu(String title) {

		this.title = title;
		this.text = null;
		this.menus = new LinkedList<ItemWatchMenu>();

		String prev = "Go Back.";
		if(!this.title.equals(prev)) {
			ItemWatchMenu exit = new ItemWatchMenu("Go Back.");
			this.menus.add(exit);
		}
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void add(ItemWatchMenu menu) {
		this.menus.add(this.menus.size()-1, menu);
	}
	public ItemWatchMenu get(int i) {
		return this.menus.get(i);
	}
	public int count() {
		return this.menus.size();
	}
	public String toString() {
		String title = (this.title != null) ? this.title + "\n\n" : "";
		String text  = (this.text  != null) ? this.text  + "\n\n" : "";
		String top = title + text;
		String list = "";
		for(int i = 0; i < this.menus.size(); i++) {
			ItemWatchMenu menu = this.menus.get(i);
			list+=(i+1) + "  " + menu.getTitle() + "\n";
		}
		return top + list;
	}
	protected String title;
	protected String text;
	protected LinkedList<ItemWatchMenu> menus;
}

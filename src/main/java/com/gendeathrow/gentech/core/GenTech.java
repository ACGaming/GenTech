package com.gendeathrow.gentech.core;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.gendeathrow.gentech.blocks.BlockGravityGenerator;
import com.gendeathrow.gentech.core.proxies.CommonProxy;
import com.gendeathrow.gentech.items.ItemGlueGun;
import com.gendeathrow.gentech.network.packet.PacketGenTech;
import com.gendeathrow.gentech.network.packet.PacketMachines;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = GenTech.MODID, version = GenTech.VERSION, name = GenTech.NAME, dependencies = "required-after:CoFHCore@[1.7.10R3.0.3,);required-after:solarcraft")
public class GenTech {
			public static final String MODID = "gentech";
		    public static final String VERSION = "1.1.3";
		    public static final String NAME = "GenTech";
		    public static final String PROXY = "com.gendeathrow.gentech.core.proxies";
		    public static final String CHANNEL = "GT_GenD";
		    
		    public static  Logger logger;
		    
		    public static GenTechTab genTechTab;
		    
		    @Instance(MODID)
		    public static GenTech instance;
		    
			@SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".CommonProxy")
			public static CommonProxy proxy;

			public SimpleNetworkWrapper network;
			
			@EventHandler
			public void preInit(FMLPreInitializationEvent event)
			{
		    	logger = event.getModLog();
		    	
		    	logger.log(Level.INFO, "Loading GenTech v"+ VERSION);
		    	
		    	genTechTab = new GenTechTab("gentech.gentab");
		    	
		    	network = NetworkRegistry.INSTANCE.newSimpleChannel(GenTech.CHANNEL);
		    	
				this.network.registerMessage(PacketGenTech.HandlerServer.class, PacketGenTech.class, 0, Side.SERVER);
				this.network.registerMessage(PacketGenTech.HandlerClient.class, PacketGenTech.class, 1, Side.CLIENT);
				this.network.registerMessage(PacketMachines.HandlerServer.class, PacketMachines.class, 3, Side.SERVER);
				this.network.registerMessage(PacketMachines.HandlerClient.class, PacketMachines.class, 4, Side.CLIENT);		
		    	
				proxy.preInit(event);
				
				proxy.registerEventHandlers();

			}

			
		    @EventHandler
		    public void init(FMLInitializationEvent event)
		    {
		    	proxy.init(event);
		    	
				
				ConfigHandler.LoadConfig();
		    	
		        MinecraftForge.EVENT_BUS.register(proxy);
		        
		    	BlockGravityGenerator.init();
		    	ItemGlueGun.init();
		    }
		    
		    @EventHandler
			public void postInit(FMLPostInitializationEvent event)
			{
				proxy.postInit(event);
				logger.log(Level.INFO, "GenTech Industrys have Loaded all nesscary materials. Please Enjoy all GenTech Products.");
			}
			
			@EventHandler
			public void serverStart(FMLServerStartingEvent event)
			{
				//MinecraftServer server = MinecraftServer.getServer();
				//ICommandManager command = server.getCommandManager();
				//ServerCommandManager manager = (ServerCommandManager) command;
				
//				manager.registerCommand(new CommandPhysics());

			}

	}
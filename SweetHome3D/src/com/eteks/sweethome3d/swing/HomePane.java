/*
 * HomePane.java 15 mai 2006
 *
 * Sweet Home 3D, Copyright (c) 2006 Emmanuel PUYBARET / eTeks <info@eteks.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.eteks.sweethome3d.swing;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.im.InputContext;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.media.j3d.Node;
import javax.media.j3d.VirtualUniverse;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.MenuElement;
import javax.swing.RootPaneContainer;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import com.eteks.sweethome3d.j3d.Ground3D;
import com.eteks.sweethome3d.j3d.OBJWriter;
import com.eteks.sweethome3d.j3d.Object3DBranchFactory;
import com.eteks.sweethome3d.model.BackgroundImage;
import com.eteks.sweethome3d.model.Camera;
import com.eteks.sweethome3d.model.CatalogPieceOfFurniture;
import com.eteks.sweethome3d.model.CollectionEvent;
import com.eteks.sweethome3d.model.CollectionListener;
import com.eteks.sweethome3d.model.Compass;
import com.eteks.sweethome3d.model.Content;
import com.eteks.sweethome3d.model.DimensionLine;
import com.eteks.sweethome3d.model.Elevatable;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomeDescriptor;
import com.eteks.sweethome3d.model.HomeEnvironment;
import com.eteks.sweethome3d.model.HomeFurnitureGroup;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.InterruptedRecorderException;
import com.eteks.sweethome3d.model.Label;
import com.eteks.sweethome3d.model.Level;
import com.eteks.sweethome3d.model.Library;
import com.eteks.sweethome3d.model.ObjectProperty;
import com.eteks.sweethome3d.model.Polyline;
import com.eteks.sweethome3d.model.RecorderException;
import com.eteks.sweethome3d.model.Room;
import com.eteks.sweethome3d.model.Selectable;
import com.eteks.sweethome3d.model.SelectionEvent;
import com.eteks.sweethome3d.model.SelectionListener;
import com.eteks.sweethome3d.model.TextStyle;
import com.eteks.sweethome3d.model.UserPreferences;
import com.eteks.sweethome3d.model.Wall;
import com.eteks.sweethome3d.plugin.HomePluginController;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.plugin.PluginManager;
import com.eteks.sweethome3d.tools.OperatingSystem;
import com.eteks.sweethome3d.tools.URLContent;
import com.eteks.sweethome3d.viewcontroller.ContentManager;
import com.eteks.sweethome3d.viewcontroller.ExportableView;
import com.eteks.sweethome3d.viewcontroller.FurnitureController;
import com.eteks.sweethome3d.viewcontroller.HomeController;
import com.eteks.sweethome3d.viewcontroller.HomeController3D;
import com.eteks.sweethome3d.viewcontroller.HomeView;
import com.eteks.sweethome3d.viewcontroller.Object3DFactory;
import com.eteks.sweethome3d.viewcontroller.PlanController;
import com.eteks.sweethome3d.viewcontroller.PlanController.Mode;
import com.eteks.sweethome3d.viewcontroller.PlanView;
import com.eteks.sweethome3d.viewcontroller.View;
import com.eteks.sweethome3d.viewcontroller.View3D;

/**
 * The MVC view that edits a home.
 * @author Emmanuel Puybaret
 */
public class HomePane extends JRootPane implements HomeView {
  private enum MenuActionType {FILE_MENU, EDIT_MENU, FURNITURE_MENU, PLAN_MENU, VIEW_3D_MENU, HELP_MENU,
      OPEN_RECENT_HOME_MENU, ALIGN_OR_DISTRIBUTE_MENU, SORT_HOME_FURNITURE_MENU, DISPLAY_HOME_FURNITURE_PROPERTY_MENU,
      MODIFY_TEXT_STYLE, LEVELS_MENU, GO_TO_POINT_OF_VIEW, SELECT_OBJECT_MENU, TOGGLE_SELECTION_MENU}

  private static final String MAIN_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY     = "com.eteks.sweethome3d.SweetHome3D.MainPaneDividerLocation";
  private static final String CATALOG_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY  = "com.eteks.sweethome3d.SweetHome3D.CatalogPaneDividerLocation";
  private static final String PLAN_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY     = "com.eteks.sweethome3d.SweetHome3D.PlanPaneDividerLocation";
  private static final String PLAN_VIEWPORT_X_VISUAL_PROPERTY                = "com.eteks.sweethome3d.SweetHome3D.PlanViewportX";
  private static final String PLAN_VIEWPORT_Y_VISUAL_PROPERTY                = "com.eteks.sweethome3d.SweetHome3D.PlanViewportY";
  private static final String FURNITURE_VIEWPORT_Y_VISUAL_PROPERTY           = "com.eteks.sweethome3d.SweetHome3D.FurnitureViewportY";
  private static final String DETACHED_VIEW_VISUAL_PROPERTY                  = ".detachedView";
  private static final String DETACHED_VIEW_DIVIDER_LOCATION_VISUAL_PROPERTY = ".detachedViewDividerLocation";
  private static final String DETACHED_VIEW_X_VISUAL_PROPERTY                = ".detachedViewX";
  private static final String DETACHED_VIEW_Y_VISUAL_PROPERTY                = ".detachedViewY";
  private static final String DETACHED_VIEW_WIDTH_VISUAL_PROPERTY            = ".detachedViewWidth";
  private static final String DETACHED_VIEW_HEIGHT_VISUAL_PROPERTY           = ".detachedViewHeight";

  private static final int    DEFAULT_SMALL_ICON_HEIGHT = Math.round(16 * SwingTools.getResolutionScale());

  private final Home            home;
  private final UserPreferences preferences;
  private final HomeController  controller;
  private JComponent            lastFocusedComponent;
  private PlanController.Mode   previousPlanControllerMode;
  private TransferHandler       catalogTransferHandler;
  private TransferHandler       furnitureTransferHandler;
  private TransferHandler       planTransferHandler;
  private TransferHandler       view3DTransferHandler;
  private boolean               transferHandlerEnabled;
  private MouseInputAdapter     furnitureCatalogDragAndDropListener;
  private boolean               clipboardEmpty = true;
  private boolean               exportAllToOBJ = true;
  private ActionMap             menuActionMap;
  private List<Action>          pluginActions;

  /**
   * Creates home view associated with its controller.
   */
  public HomePane(Home home, UserPreferences preferences,
                  final HomeController controller) {
    this.home = home;
    this.preferences = preferences;
    this.controller = controller;

    if (!Boolean.getBoolean("com.eteks.sweethome3d.j3d.useOffScreen3DView")) {
      JPopupMenu.setDefaultLightWeightPopupEnabled(false);
      ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    }

    createActions(home, preferences, controller);
    createMenuActions(preferences, controller);
    createPluginActions(controller instanceof HomePluginController
        ? ((HomePluginController)controller).getPlugins()
        : null);
    initActions(preferences);
    createTransferHandlers(home, controller);
    addLevelVisibilityListener(home);
    addUserPreferencesListener(preferences);
    addPlanControllerListener(controller.getPlanController());
    addFocusListener();
    updateFocusTraversalPolicy();
    addClipboardListener();
    JMenuBar homeMenuBar = createMenuBar(home, preferences, controller);
    setJMenuBar(homeMenuBar);
    addHomeListeners(home, controller);
    Container contentPane = getContentPane();
    contentPane.add(createToolBar(home, preferences), BorderLayout.NORTH);
    contentPane.add(createMainPane(home, preferences, controller));
    if (OperatingSystem.isMacOSXLeopardOrSuperior()) {
      // Under Mac OS X 10.5, add some dummy labels at left and right borders
      // to avoid the tool bar to be attached on these borders
      // (segmented buttons created on this system aren't properly rendered
      // when they are aligned vertically)
      contentPane.add(new JLabel(), BorderLayout.WEST);
      contentPane.add(new JLabel(), BorderLayout.EAST);
    }

    disableMenuItemsDuringDragAndDrop(controller.getPlanController().getView(), homeMenuBar);
    // Change component orientation
    applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
  }

  /**
   * Creates the action map of this component.
   */
  private void createActions(Home home,
                             UserPreferences preferences,
                             final HomeController controller) {
    createAction(ActionType.NEW_HOME, preferences, controller, "newHome");
    createAction(ActionType.NEW_HOME_FROM_EXAMPLE, preferences, controller, "newHomeFromExample");
    createAction(ActionType.OPEN, preferences, controller, "open");
    createAction(ActionType.DELETE_RECENT_HOMES, preferences, controller, "deleteRecentHomes");
    createAction(ActionType.CLOSE, preferences, controller, "close");
    createAction(ActionType.SAVE, preferences, controller, "save");
    createAction(ActionType.SAVE_AS, preferences, controller, "saveAs");
    createAction(ActionType.SAVE_AND_COMPRESS, preferences, controller, "saveAndCompress");
    createAction(ActionType.PAGE_SETUP, preferences, controller, "setupPage");
    createAction(ActionType.PRINT_PREVIEW, preferences, controller, "previewPrint");
    createAction(ActionType.PRINT, preferences, controller, "print");
    createAction(ActionType.PRINT_TO_PDF, preferences, controller, "printToPDF");
    createAction(ActionType.PREFERENCES, preferences, controller, "editPreferences");
    createAction(ActionType.EXIT, preferences, controller, "exit");

    createAction(ActionType.UNDO, preferences, controller, "undo");
    createAction(ActionType.REDO, preferences, controller, "redo");
    createClipboardAction(ActionType.CUT, preferences, TransferHandler.getCutAction(), true);
    createClipboardAction(ActionType.COPY, preferences, TransferHandler.getCopyAction(), true);
    createClipboardAction(ActionType.PASTE, preferences, TransferHandler.getPasteAction(), false);
    createAction(ActionType.PASTE_TO_GROUP, preferences, controller, "pasteToGroup");
    createAction(ActionType.PASTE_STYLE, preferences, controller, "pasteStyle");
    createAction(ActionType.DELETE, preferences, controller, "delete");
    createAction(ActionType.SELECT_ALL, preferences, controller, "selectAll");

    createAction(ActionType.ADD_HOME_FURNITURE, preferences, controller, "addHomeFurniture");
    createAction(ActionType.ADD_FURNITURE_TO_GROUP, preferences, controller, "addFurnitureToGroup");
    final FurnitureController furnitureController = controller.getFurnitureController();
    createAction(ActionType.DELETE_HOME_FURNITURE, preferences, furnitureController, "deleteSelection");
    createAction(ActionType.MODIFY_FURNITURE, preferences, controller, "modifySelectedFurniture");
    createAction(ActionType.GROUP_FURNITURE, preferences, furnitureController, "groupSelectedFurniture");
    createAction(ActionType.UNGROUP_FURNITURE, preferences, furnitureController, "ungroupSelectedFurniture");
    createAction(ActionType.ALIGN_FURNITURE_ON_TOP, preferences, furnitureController, "alignSelectedFurnitureOnTop");
    createAction(ActionType.ALIGN_FURNITURE_ON_BOTTOM, preferences, furnitureController, "alignSelectedFurnitureOnBottom");
    createAction(ActionType.ALIGN_FURNITURE_ON_LEFT, preferences, furnitureController, "alignSelectedFurnitureOnLeft");
    createAction(ActionType.ALIGN_FURNITURE_ON_RIGHT, preferences, furnitureController, "alignSelectedFurnitureOnRight");
    createAction(ActionType.ALIGN_FURNITURE_ON_FRONT_SIDE, preferences, furnitureController, "alignSelectedFurnitureOnFrontSide");
    createAction(ActionType.ALIGN_FURNITURE_ON_BACK_SIDE, preferences, furnitureController, "alignSelectedFurnitureOnBackSide");
    createAction(ActionType.ALIGN_FURNITURE_ON_LEFT_SIDE, preferences, furnitureController, "alignSelectedFurnitureOnLeftSide");
    createAction(ActionType.ALIGN_FURNITURE_ON_RIGHT_SIDE, preferences, furnitureController, "alignSelectedFurnitureOnRightSide");
    createAction(ActionType.ALIGN_FURNITURE_SIDE_BY_SIDE, preferences, furnitureController, "alignSelectedFurnitureSideBySide");
    createAction(ActionType.DISTRIBUTE_FURNITURE_HORIZONTALLY, preferences, furnitureController, "distributeSelectedFurnitureHorizontally");
    createAction(ActionType.DISTRIBUTE_FURNITURE_VERTICALLY, preferences, furnitureController, "distributeSelectedFurnitureVertically");
    createAction(ActionType.RESET_FURNITURE_ELEVATION, preferences, furnitureController, "resetFurnitureElevation");
    final HomeController3D homeController3D = controller.getHomeController3D();
    if (homeController3D.getView() != null) {
      createAction(ActionType.IMPORT_FURNITURE, preferences, controller, "importFurniture");
    }
    createAction(ActionType.IMPORT_FURNITURE_LIBRARY, preferences, controller, "importFurnitureLibrary");
    createAction(ActionType.IMPORT_TEXTURE, preferences, controller, "importTexture");
    createAction(ActionType.IMPORT_TEXTURES_LIBRARY, preferences, controller, "importTexturesLibrary");
    createAction(ActionType.SORT_HOME_FURNITURE_BY_CATALOG_ID, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.CATALOG_ID.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_NAME, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.NAME.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_DESCRIPTION, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.DESCRIPTION.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_CREATOR, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.CREATOR.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_LICENSE, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.LICENSE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_WIDTH, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.WIDTH.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_DEPTH, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.DEPTH.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_HEIGHT, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.HEIGHT.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_X, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.X.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_Y, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.Y.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_ELEVATION, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.ELEVATION.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_ANGLE, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.ANGLE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_LEVEL, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.LEVEL.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_MODEL_SIZE, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.MODEL_SIZE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_COLOR, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.COLOR.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_TEXTURE, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.TEXTURE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_MOVABILITY, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.MOVABLE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_TYPE, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.DOOR_OR_WINDOW.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_VISIBILITY, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.VISIBLE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_PRICE, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.PRICE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX_PERCENTAGE, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX_PERCENTAGE.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_PRICE_VALUE_ADDED_TAX_INCLUDED, preferences,
        furnitureController, "toggleFurnitureSort", HomePieceOfFurniture.SortableProperty.PRICE_VALUE_ADDED_TAX_INCLUDED.name());
    createAction(ActionType.SORT_HOME_FURNITURE_BY_DESCENDING_ORDER, preferences,
        furnitureController, "toggleFurnitureSortOrder");
    createAction(ActionType.DISPLAY_HOME_FURNITURE_CATALOG_ID, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.CATALOG_ID.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_NAME, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.NAME.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_DESCRIPTION, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.DESCRIPTION.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_CREATOR, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.CREATOR.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_LICENSE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.LICENSE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_WIDTH, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.WIDTH.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_DEPTH, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.DEPTH.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_HEIGHT, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.HEIGHT.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_X, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.X.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_Y, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.Y.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_ELEVATION, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.ELEVATION.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_ANGLE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.ANGLE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_LEVEL, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.LEVEL.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_MODEL_SIZE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.MODEL_SIZE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_COLOR, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.COLOR.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_TEXTURE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.TEXTURE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_MOVABLE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.MOVABLE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_DOOR_OR_WINDOW, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.DOOR_OR_WINDOW.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_VISIBLE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.VISIBLE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_PRICE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.PRICE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX_PERCENTAGE, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX_PERCENTAGE.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX.name());
    createAction(ActionType.DISPLAY_HOME_FURNITURE_PRICE_VALUE_ADDED_TAX_INCLUDED, preferences,
        furnitureController, "toggleFurnitureVisibleProperty", HomePieceOfFurniture.SortableProperty.PRICE_VALUE_ADDED_TAX_INCLUDED.name());
    createAction(ActionType.EXPORT_TO_CSV, preferences, controller, "exportToCSV");

    PlanController planController = controller.getPlanController();
    if (planController.getView() != null) {
      createAction(ActionType.SELECT_ALL_AT_ALL_LEVELS, preferences, planController, "selectAllAtAllLevels");
      ButtonGroup modeGroup = new ButtonGroup();
      createToggleAction(ActionType.SELECT, planController.getMode() == PlanController.Mode.SELECTION, modeGroup,
          preferences, controller, "setMode", PlanController.Mode.SELECTION);
      createToggleAction(ActionType.PAN, planController.getMode() == PlanController.Mode.PANNING, modeGroup,
          preferences, controller, "setMode", PlanController.Mode.PANNING);
      createToggleAction(ActionType.CREATE_WALLS, planController.getMode() == PlanController.Mode.WALL_CREATION, modeGroup,
          preferences, controller, "setMode", PlanController.Mode.WALL_CREATION);
      createToggleAction(ActionType.CREATE_ROOMS, planController.getMode() == PlanController.Mode.ROOM_CREATION, modeGroup,
          preferences, controller, "setMode", PlanController.Mode.ROOM_CREATION);
      createToggleAction(ActionType.CREATE_POLYLINES, planController.getMode() == PlanController.Mode.POLYLINE_CREATION, modeGroup,
          preferences, controller, "setMode", PlanController.Mode.POLYLINE_CREATION);
      createToggleAction(ActionType.CREATE_DIMENSION_LINES, planController.getMode() == PlanController.Mode.DIMENSION_LINE_CREATION, modeGroup,
          preferences, controller, "setMode", PlanController.Mode.DIMENSION_LINE_CREATION);
      createToggleAction(ActionType.CREATE_LABELS, planController.getMode() == PlanController.Mode.LABEL_CREATION, modeGroup,
          preferences, controller, "setMode", PlanController.Mode.LABEL_CREATION);
      createAction(ActionType.DELETE_SELECTION, preferences, planController, "deleteSelection");
      createAction(ActionType.LOCK_BASE_PLAN, preferences, planController, "lockBasePlan");
      createAction(ActionType.UNLOCK_BASE_PLAN, preferences, planController, "unlockBasePlan");
      createAction(ActionType.ENABLE_MAGNETISM, preferences, controller, "enableMagnetism");
      createAction(ActionType.DISABLE_MAGNETISM, preferences, controller, "disableMagnetism");
      createAction(ActionType.FLIP_HORIZONTALLY, preferences, planController, "flipHorizontally");
      createAction(ActionType.FLIP_VERTICALLY, preferences, planController, "flipVertically");
      createAction(ActionType.MODIFY_COMPASS, preferences, planController, "modifyCompass");
      createAction(ActionType.MODIFY_WALL, preferences, planController, "modifySelectedWalls");
      createAction(ActionType.JOIN_WALLS, preferences, planController, "joinSelectedWalls");
      createAction(ActionType.REVERSE_WALL_DIRECTION, preferences, planController, "reverseSelectedWallsDirection");
      createAction(ActionType.SPLIT_WALL, preferences, planController, "splitSelectedWall");
      createAction(ActionType.MODIFY_ROOM, preferences, planController, "modifySelectedRooms");
      // ADD_ROOM_POINT, DELETE_ROOM_POINT and RECOMPUTE_ROOM_POINTS actions are actually defined later in updateRoomActions
      createAction(ActionType.ADD_ROOM_POINT, preferences);
      createAction(ActionType.DELETE_ROOM_POINT, preferences);
      createAction(ActionType.RECOMPUTE_ROOM_POINTS, preferences);
      createAction(ActionType.MODIFY_POLYLINE, preferences, planController, "modifySelectedPolylines");
      createAction(ActionType.MODIFY_DIMENSION_LINE, preferences, planController, "modifySelectedDimensionLines");
      createAction(ActionType.MODIFY_LABEL, preferences, planController, "modifySelectedLabels");
      createAction(ActionType.INCREASE_TEXT_SIZE, preferences, planController, "increaseTextSize");
      createAction(ActionType.DECREASE_TEXT_SIZE, preferences, planController, "decreaseTextSize");
      // Use special toggle models for bold and italic check box menu items and tool bar buttons
      // that are selected texts in home selected items are all bold or italic
      Action toggleBoldAction = createAction(ActionType.TOGGLE_BOLD_STYLE, preferences, planController, "toggleBoldStyle");
      toggleBoldAction.putValue(ResourceAction.TOGGLE_BUTTON_MODEL, createBoldStyleToggleModel(home, preferences));
      Action toggleItalicAction = createAction(ActionType.TOGGLE_ITALIC_STYLE, preferences, planController, "toggleItalicStyle");
      toggleItalicAction.putValue(ResourceAction.TOGGLE_BUTTON_MODEL, createItalicStyleToggleModel(home, preferences));
      createAction(ActionType.IMPORT_BACKGROUND_IMAGE, preferences, controller, "importBackgroundImage");
      createAction(ActionType.MODIFY_BACKGROUND_IMAGE, preferences, controller, "modifyBackgroundImage");
      createAction(ActionType.HIDE_BACKGROUND_IMAGE, preferences, controller, "hideBackgroundImage");
      createAction(ActionType.SHOW_BACKGROUND_IMAGE, preferences, controller, "showBackgroundImage");
      createAction(ActionType.DELETE_BACKGROUND_IMAGE, preferences, controller, "deleteBackgroundImage");
      createAction(ActionType.ADD_LEVEL, preferences, planController, "addLevel");
      createAction(ActionType.ADD_LEVEL_AT_SAME_ELEVATION, preferences, planController, "addLevelAtSameElevation");
      createAction(ActionType.MAKE_LEVEL_VIEWABLE, preferences, planController, "toggleSelectedLevelViewability");
      createAction(ActionType.MAKE_LEVEL_UNVIEWABLE, preferences, planController, "toggleSelectedLevelViewability");
      createAction(ActionType.MAKE_LEVEL_ONLY_VIEWABLE_ONE, preferences, planController, "setSelectedLevelOnlyViewable");
      createAction(ActionType.MAKE_ALL_LEVELS_VIEWABLE, preferences, planController, "setAllLevelsViewable");
      createAction(ActionType.MODIFY_LEVEL, preferences, planController, "modifySelectedLevel");
      createAction(ActionType.DELETE_LEVEL, preferences, planController, "deleteSelectedLevel");
      createAction(ActionType.ZOOM_IN, preferences, controller, "zoomIn");
      createAction(ActionType.ZOOM_OUT, preferences, controller, "zoomOut");
      createAction(ActionType.EXPORT_TO_SVG, preferences, controller, "exportToSVG");
    }

    if (homeController3D.getView() != null) {
      // SELECT_OBJECT and TOGGLE_SELECTION actions are actually defined later in updatePickingActions
      createAction(ActionType.SELECT_OBJECT, preferences);
      createAction(ActionType.TOGGLE_SELECTION, preferences);

      ButtonGroup viewGroup = new ButtonGroup();
      createToggleAction(ActionType.VIEW_FROM_TOP, home.getCamera() == home.getTopCamera(), viewGroup,
          preferences, homeController3D, "viewFromTop");
      createToggleAction(ActionType.VIEW_FROM_OBSERVER, home.getCamera() == home.getObserverCamera(), viewGroup,
          preferences, homeController3D, "viewFromObserver");
      createAction(ActionType.MODIFY_OBSERVER, preferences, planController, "modifyObserverCamera");
      createAction(ActionType.STORE_POINT_OF_VIEW, preferences, controller, "storeCamera");
      createAction(ActionType.DELETE_POINTS_OF_VIEW, preferences, controller, "deleteCameras");
      createAction(ActionType.DETACH_3D_VIEW, preferences, controller, "detachView", controller.getHomeController3D().getView());
      createAction(ActionType.ATTACH_3D_VIEW, preferences, controller, "attachView", controller.getHomeController3D().getView());

      ButtonGroup displayLevelGroup = new ButtonGroup();
      boolean allLevelsVisible = home.getEnvironment().isAllLevelsVisible();
      createToggleAction(ActionType.DISPLAY_ALL_LEVELS, allLevelsVisible, displayLevelGroup, preferences,
          homeController3D, "displayAllLevels");
      createToggleAction(ActionType.DISPLAY_SELECTED_LEVEL, !allLevelsVisible, displayLevelGroup, preferences,
          homeController3D, "displaySelectedLevel");
      createAction(ActionType.MODIFY_3D_ATTRIBUTES, preferences, homeController3D, "modifyAttributes");
      createAction(ActionType.CREATE_PHOTO, preferences, controller, "createPhoto");
      createAction(ActionType.CREATE_PHOTOS_AT_POINTS_OF_VIEW, preferences, controller, "createPhotos");
      createAction(ActionType.CREATE_VIDEO, preferences, controller, "createVideo");
      createAction(ActionType.EXPORT_TO_OBJ, preferences, controller, "exportToOBJ");
    }

    createAction(ActionType.HELP, preferences, controller, "help");
    createAction(ActionType.ABOUT, preferences, controller, "about");

    createAdditionalActions(home, controller);
  }

  /**
   * Adds to the action map of this component actions created from additional properties.
   */
  private void createAdditionalActions(Home home, final HomeController controller) {
    ActionMap actionMap = getActionMap();
    final FurnitureController furnitureController = controller.getFurnitureController();
    for (final ObjectProperty property : home.getFurnitureAdditionalProperties()) {
      AbstractAction menuItemAction = new AbstractAction(property.getDisplayedName()) {
          public void actionPerformed(ActionEvent ev) {
            furnitureController.toggleFurnitureSort(property.getName());
          }
        };
      menuItemAction.putValue(ResourceAction.RESOURCE_PREFIX, SORT_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX);
      actionMap.put(SORT_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX + property.getName(), menuItemAction);

      menuItemAction = new AbstractAction(property.getDisplayedName()) {
          public void actionPerformed(ActionEvent ev) {
            furnitureController.toggleFurnitureVisibleProperty(property.getName());
          }
        };
      menuItemAction.putValue(ResourceAction.RESOURCE_PREFIX, DISPLAY_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX);
      actionMap.put(DISPLAY_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX + property.getName(), menuItemAction);
    }
  }

  /**
   * Returns a new <code>ControllerAction</code> object that calls on <code>controller</code> a given
   * <code>method</code> with its <code>parameters</code>. This action is added to the action map of this component.
   */
  private Action createAction(ActionType actionType,
                              UserPreferences preferences,
                              Object controller,
                              String method,
                              Object ... parameters) {
    try {
      ControllerAction action = new ControllerAction(
          preferences, HomePane.class, actionType.name(), controller, method, parameters);
      getActionMap().put(actionType, action);
      return action;
    } catch (NoSuchMethodException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Returns a new <code>ResourceAction</code> object that does nothing.
   * This action is added to the action map of this component.
   */
  private Action createAction(ActionType actionType,
                              UserPreferences preferences) {
    ResourceAction action = new ResourceAction(preferences, HomePane.class, actionType.name()) {
        @Override
        public void actionPerformed(ActionEvent ev) {
        }
      };
    getActionMap().put(actionType, action);
    return action;
  }

  /**
   * Returns a new <code>ControllerAction</code> object associated with a <code>ToggleButtonModel</code> instance
   * set as selected or not.
   */
  private Action createToggleAction(ActionType actionType,
                                    boolean selected,
                                    ButtonGroup group,
                                    UserPreferences preferences,
                                    Object controller,
                                    String method,
                                    Object ... parameters) {
    Action action = createAction(actionType, preferences, controller, method, parameters);
    JToggleButton.ToggleButtonModel toggleButtonModel = new JToggleButton.ToggleButtonModel();
    if (group != null) {
      toggleButtonModel.setGroup(group);
    }
    toggleButtonModel.setSelected(selected);
    action.putValue(ResourceAction.TOGGLE_BUTTON_MODEL, toggleButtonModel);
    return action;
  }

  /**
   * Creates a <code>ReourceAction</code> object that calls
   * <code>actionPerfomed</code> method on a given
   * existing <code>clipboardAction</code> with a source equal to focused component.
   */
  private void createClipboardAction(ActionType actionType,
                                     UserPreferences preferences,
                                     final Action clipboardAction,
                                     final boolean copyAction) {
    getActionMap().put(actionType,
        new ResourceAction (preferences, HomePane.class, actionType.name()) {
          public void actionPerformed(ActionEvent ev) {
            if (copyAction) {
              clipboardEmpty = false;
            }
            ev = new ActionEvent(lastFocusedComponent, ActionEvent.ACTION_PERFORMED, null);
            clipboardAction.actionPerformed(ev);
          }
        });
  }

  /**
   * Create the actions map used to create menus of this component.
   */
  private void createMenuActions(UserPreferences preferences,
                                 HomeController controller) {
    this.menuActionMap = new ActionMap();
    createMenuAction(preferences, MenuActionType.FILE_MENU, "File");
    createMenuAction(preferences, MenuActionType.EDIT_MENU, "Edit");
    createMenuAction(preferences, MenuActionType.FURNITURE_MENU, "Furniture");
    createMenuAction(preferences, MenuActionType.PLAN_MENU, "Plan");
    createMenuAction(preferences, MenuActionType.VIEW_3D_MENU, "3D view");
    createMenuAction(preferences, MenuActionType.HELP_MENU, "Help");
    createMenuAction(preferences, MenuActionType.OPEN_RECENT_HOME_MENU, null);
    createMenuAction(preferences, MenuActionType.SORT_HOME_FURNITURE_MENU, null);
    createMenuAction(preferences, MenuActionType.ALIGN_OR_DISTRIBUTE_MENU, null);
    createMenuAction(preferences, MenuActionType.DISPLAY_HOME_FURNITURE_PROPERTY_MENU, null);
    createMenuAction(preferences, MenuActionType.MODIFY_TEXT_STYLE, null);
    createMenuAction(preferences, MenuActionType.LEVELS_MENU, null);
    createMenuAction(preferences, MenuActionType.GO_TO_POINT_OF_VIEW, null);
    createMenuAction(preferences, MenuActionType.SELECT_OBJECT_MENU, null);
    createMenuAction(preferences, MenuActionType.TOGGLE_SELECTION_MENU, null);
  }

  /**
   * Creates a <code>ResourceAction</code> object stored in menu action map.
   */
  private void createMenuAction(UserPreferences preferences,
                                MenuActionType action,
                                String unlocalizedName) {
    ResourceAction menuAction = new ResourceAction(preferences, HomePane.class, action.name(), true);
    menuAction.putValue(ResourceAction.UNLOCALIZED_NAME, unlocalizedName);
    this.menuActionMap.put(action, menuAction);
  }

  /**
   * Creates the Swing actions matching each actions available in <code>plugins</code>.
   */
  private void createPluginActions(List<Plugin> plugins) {
    this.pluginActions = new ArrayList<Action>();
    if (plugins != null) {
      for (Plugin plugin : plugins) {
        for (final PluginAction pluginAction : plugin.getActions()) {
          // Create a Swing action adapter to plug-in action
          this.pluginActions.add(new ActionAdapter(pluginAction));
        }
      }
    }
  }

  /**
   * Creates components transfer handlers.
   */
  private void createTransferHandlers(Home home,
                                      HomeController controller) {
    this.catalogTransferHandler =
        new FurnitureCatalogTransferHandler(controller.getContentManager(),
            controller.getFurnitureCatalogController(), controller.getFurnitureController());
    this.furnitureTransferHandler =
        new FurnitureTransferHandler(home, controller.getContentManager(), controller);
    this.planTransferHandler =
        new PlanTransferHandler(home, controller.getContentManager(), controller);
    this.view3DTransferHandler = new Component3DTransferHandler(home, controller);
  }

  /**
   * Adds property change listeners to <code>home</code> to update
   * View from top and View from observer toggle models according to used camera
   * as well as display and sort furniture menu items.
   */
  private void addHomeListeners(final Home home, final HomeController controller) {
    home.addPropertyChangeListener(Home.Property.CAMERA,
        new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            setToggleButtonModelSelected(ActionType.VIEW_FROM_TOP, home.getCamera() == home.getTopCamera());
            setToggleButtonModelSelected(ActionType.VIEW_FROM_OBSERVER, home.getCamera() == home.getObserverCamera());
          }
        });

    // Retrieve menu bar now because it moves to parent's view under macOS
    final JMenuBar menuBar = getJMenuBar();
    home.addPropertyChangeListener(Home.Property.FURNITURE_ADDITIONAL_PROPERTIES,
        new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            createAdditionalActions(home, controller);
            // Update menus
            updateFurnitureSortMenu(findMenu(menuBar, MenuActionType.SORT_HOME_FURNITURE_MENU), home);
            updateDisplayPropertyMenu(findMenu(menuBar, MenuActionType.DISPLAY_HOME_FURNITURE_PROPERTY_MENU), home);
            JComponent furnitureView = (JComponent)controller.getFurnitureController().getView();
            if (furnitureView != null) {
              JPopupMenu furniturePopupMenu = furnitureView.getComponentPopupMenu();
              if (furniturePopupMenu != null) {
                updateFurnitureSortMenu(findMenu(furniturePopupMenu, MenuActionType.SORT_HOME_FURNITURE_MENU), home);
                updateDisplayPropertyMenu(findMenu(furniturePopupMenu, MenuActionType.DISPLAY_HOME_FURNITURE_PROPERTY_MENU), home);
              }
            }
          }

          private JMenu findMenu(MenuElement menu, MenuActionType actionType) {
            if (menu instanceof JMenu
                && ((JMenu)menu).getAction() != null
                && actionType.name().equals(((JMenu)menu).getAction().getValue(ResourceAction.RESOURCE_PREFIX))) {
              return (JMenu)menu;
            } else {
              for (MenuElement element : menu.getSubElements()) {
                JMenu menuComponent = findMenu(element, actionType);
                if (menuComponent != null) {
                  return menuComponent;
                }
              }
              return null;
            }
          }
        });
  }

  /**
   * Changes the selection of the toggle model matching the given action.
   */
  private void setToggleButtonModelSelected(ActionType actionType, boolean selected) {
    ((JToggleButton.ToggleButtonModel)getActionMap().get(actionType).getValue(ResourceAction.TOGGLE_BUTTON_MODEL)).
        setSelected(selected);
  }

  /**
   * Adds listener to <code>home</code> to update
   * Display all levels and Display selected level toggle models
   * according their visibility.
   */
  private void addLevelVisibilityListener(final Home home) {
    home.getEnvironment().addPropertyChangeListener(HomeEnvironment.Property.ALL_LEVELS_VISIBLE,
        new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            boolean allLevelsVisible = home.getEnvironment().isAllLevelsVisible();
            setToggleButtonModelSelected(ActionType.DISPLAY_ALL_LEVELS, allLevelsVisible);
            setToggleButtonModelSelected(ActionType.DISPLAY_SELECTED_LEVEL, !allLevelsVisible);
          }
        });
  }

  /**
   * Adds a property change listener to <code>preferences</code> to update
   * actions when some preferences change.
   */
  private void addUserPreferencesListener(UserPreferences preferences) {
    UserPreferencesChangeListener listener = new UserPreferencesChangeListener(this);
    preferences.addPropertyChangeListener(UserPreferences.Property.LANGUAGE, listener);
    preferences.addPropertyChangeListener(UserPreferences.Property.CURRENCY, listener);
    preferences.addPropertyChangeListener(UserPreferences.Property.VALUE_ADDED_TAX_ENABLED, listener);
    preferences.addPropertyChangeListener(UserPreferences.Property.EDITING_IN_3D_VIEW_ENABLED, listener);
  }

  /**
   * Preferences property listener bound to this component with a weak reference to avoid
   * strong link between preferences and this component.
   */
  private static class UserPreferencesChangeListener implements PropertyChangeListener {
    private WeakReference<HomePane> homePane;

    public UserPreferencesChangeListener(HomePane homePane) {
      this.homePane = new WeakReference<HomePane>(homePane);
    }

    public void propertyChange(PropertyChangeEvent ev) {
      // If home pane was garbage collected, remove this listener from preferences
      HomePane homePane = this.homePane.get();
      UserPreferences preferences = (UserPreferences)ev.getSource();
      UserPreferences.Property property = UserPreferences.Property.valueOf(ev.getPropertyName());
      if (homePane == null) {
        preferences.removePropertyChangeListener(property, this);
      } else {
        ActionMap actionMap = homePane.getActionMap();
        switch (property) {
          case LANGUAGE :
            SwingTools.updateSwingResourceLanguage((UserPreferences)ev.getSource());
            break;
          case CURRENCY :
            actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_PRICE).putValue(ResourceAction.VISIBLE, ev.getNewValue() != null);
            actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_PRICE).putValue(ResourceAction.VISIBLE, ev.getNewValue() != null);
            break;
          case VALUE_ADDED_TAX_ENABLED :
            actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX_PERCENTAGE).putValue(ResourceAction.VISIBLE, Boolean.TRUE.equals(ev.getNewValue()));
            actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX).putValue(ResourceAction.VISIBLE, Boolean.TRUE.equals(ev.getNewValue()));
            actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_PRICE_VALUE_ADDED_TAX_INCLUDED).putValue(ResourceAction.VISIBLE, Boolean.TRUE.equals(ev.getNewValue()));
            actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX_PERCENTAGE).putValue(ResourceAction.VISIBLE, Boolean.TRUE.equals(ev.getNewValue()));
            actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX).putValue(ResourceAction.VISIBLE, Boolean.TRUE.equals(ev.getNewValue()));
            actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_PRICE_VALUE_ADDED_TAX_INCLUDED).putValue(ResourceAction.VISIBLE, Boolean.TRUE.equals(ev.getNewValue()));
            break;
          case EDITING_IN_3D_VIEW_ENABLED :
            JComponent view3D = (JComponent)homePane.controller.getHomeController3D().getView();
            if (view3D != null) {
              view3D.setTransferHandler(preferences.isEditingIn3DViewEnabled() ? homePane.view3DTransferHandler : null);
            }
            break;
        }
      }
    }
  }

  /**
   * Sets whether some actions should be visible or not.
   */
  private void initActions(UserPreferences preferences) {
    ActionMap actionMap = getActionMap();
    actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_CATALOG_ID).putValue(ResourceAction.VISIBLE, Boolean.FALSE);
    actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_CATALOG_ID).putValue(ResourceAction.VISIBLE, Boolean.FALSE);
    actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_PRICE).putValue(ResourceAction.VISIBLE, preferences.getCurrency() != null);
    actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_PRICE).putValue(ResourceAction.VISIBLE, preferences.getCurrency() != null);
    actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX_PERCENTAGE).putValue(ResourceAction.VISIBLE, preferences.isValueAddedTaxEnabled());
    actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX).putValue(ResourceAction.VISIBLE, preferences.isValueAddedTaxEnabled());
    actionMap.get(ActionType.DISPLAY_HOME_FURNITURE_PRICE_VALUE_ADDED_TAX_INCLUDED).putValue(ResourceAction.VISIBLE, preferences.isValueAddedTaxEnabled());
    actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX_PERCENTAGE).putValue(ResourceAction.VISIBLE, preferences.isValueAddedTaxEnabled());
    actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX).putValue(ResourceAction.VISIBLE, preferences.isValueAddedTaxEnabled());
    actionMap.get(ActionType.SORT_HOME_FURNITURE_BY_PRICE_VALUE_ADDED_TAX_INCLUDED).putValue(ResourceAction.VISIBLE, preferences.isValueAddedTaxEnabled());
  }

  /**
   * Adds a property change listener to <code>planController</code> to update
   * Select and Create walls toggle models according to current mode.
   */
  private void addPlanControllerListener(final PlanController planController) {
    planController.addPropertyChangeListener(PlanController.Property.MODE,
        new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            Mode mode = planController.getMode();
            setToggleButtonModelSelected(ActionType.SELECT, mode == PlanController.Mode.SELECTION);
            setToggleButtonModelSelected(ActionType.PAN, mode == PlanController.Mode.PANNING);
            setToggleButtonModelSelected(ActionType.CREATE_WALLS, mode == PlanController.Mode.WALL_CREATION);
            setToggleButtonModelSelected(ActionType.CREATE_ROOMS, mode == PlanController.Mode.ROOM_CREATION);
            setToggleButtonModelSelected(ActionType.CREATE_POLYLINES, mode == PlanController.Mode.POLYLINE_CREATION);
            setToggleButtonModelSelected(ActionType.CREATE_DIMENSION_LINES, mode == PlanController.Mode.DIMENSION_LINE_CREATION);
            setToggleButtonModelSelected(ActionType.CREATE_LABELS, mode == PlanController.Mode.LABEL_CREATION);
          }
        });
  }

  /**
   * Adds a focus change listener to report to controller focus changes.
   */
  private void addFocusListener() {
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("currentFocusCycleRoot",
        new FocusCycleRootChangeListener(this));
  }

  /**
   * Property listener bound to this component with a weak reference to avoid
   * strong link between KeyboardFocusManager and this component.
   */
  private static class FocusCycleRootChangeListener implements PropertyChangeListener {
    private WeakReference<HomePane> homePane;
    private PropertyChangeListener  focusChangeListener;

    public FocusCycleRootChangeListener(HomePane homePane) {
      this.homePane = new WeakReference<HomePane>(homePane);
    }

    public void propertyChange(PropertyChangeEvent ev) {
      // If home pane was garbage collected, remove this listener from KeyboardFocusManager
      final HomePane homePane = this.homePane.get();
      if (homePane == null) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().
            removePropertyChangeListener("currentFocusCycleRoot", this);
      } else {
        if (SwingUtilities.isDescendingFrom(homePane, (Component)ev.getOldValue())) {
          KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener("focusOwner",
              this.focusChangeListener);
          this.focusChangeListener = null;
        } else if (SwingUtilities.isDescendingFrom(homePane, (Component)ev.getNewValue())) {
          this.focusChangeListener = new FocusOwnerChangeListener(homePane);
          KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner",
              this.focusChangeListener);
        }
      }
    }
  }

  /**
   * Property listener bound to this component with a weak reference to avoid
   * strong link between KeyboardFocusManager and this component.
   */
  private static class FocusOwnerChangeListener implements PropertyChangeListener {
    private WeakReference<HomePane> homePane;

    private FocusOwnerChangeListener(HomePane homePane) {
      this.homePane = new WeakReference<HomePane>(homePane);
    }

    public void propertyChange(PropertyChangeEvent ev) {
      // If home pane was garbage collected, remove this listener from KeyboardFocusManager
      final HomePane homePane = this.homePane.get();
      if (homePane == null) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener("focusOwner", this);
      } else {
        if (homePane.lastFocusedComponent != null) {
          // Update component which lost focused
          JComponent lostFocusedComponent = homePane.lastFocusedComponent;
          if (SwingUtilities.isDescendingFrom(lostFocusedComponent, SwingUtilities.getWindowAncestor(homePane))) {
            lostFocusedComponent.removeKeyListener(homePane.specialKeysListener);
            // Restore previous plan mode if plan view had focus and window is deactivated
            if (homePane.previousPlanControllerMode != null
                && (lostFocusedComponent == homePane.controller.getPlanController().getView()
                || ev.getNewValue() == null)) {
              homePane.controller.getPlanController().setMode(homePane.previousPlanControllerMode);
              homePane.previousPlanControllerMode = null;
            }
          }
        }

        if (ev.getNewValue() != null) {
          // Retrieve component which gained focused
          Component gainedFocusedComponent = (Component)ev.getNewValue();
          if (SwingUtilities.isDescendingFrom(gainedFocusedComponent, SwingUtilities.getWindowAncestor(homePane))
              && gainedFocusedComponent instanceof JComponent) {
            View [] focusableViews = {homePane.controller.getFurnitureCatalogController().getView(),
                                      homePane.controller.getFurnitureController().getView(),
                                      homePane.controller.getPlanController().getView(),
                                      homePane.controller.getHomeController3D().getView()};
            // Notify controller that active view changed
            for (View view : focusableViews) {
              if (view != null && SwingUtilities.isDescendingFrom(gainedFocusedComponent, (JComponent)view)) {
                homePane.controller.focusedViewChanged(view);
                gainedFocusedComponent.addKeyListener(homePane.specialKeysListener);
                // Update the component used by clipboard actions
                homePane.lastFocusedComponent = (JComponent)gainedFocusedComponent;
                break;
              }
            }
          }
        }
      }
    }
  }

  private KeyListener specialKeysListener = new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        // Temporarily toggle plan controller mode to panning mode when space bar is pressed
        PlanController planController = controller.getPlanController();
        if (ev.getKeyCode() == KeyEvent.VK_SPACE
            && (ev.getModifiers() & (KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK | KeyEvent.META_MASK)) == 0
            && getActionMap().get(ActionType.PAN).getValue(Action.NAME) != null
            && planController.getMode() != PlanController.Mode.PANNING
            && !planController.isModificationState()
            && SwingUtilities.isDescendingFrom(lastFocusedComponent, HomePane.this)
            && !isSpaceUsedByComponent(lastFocusedComponent)) {
          previousPlanControllerMode = planController.getMode();
          planController.setMode(PlanController.Mode.PANNING);
          ev.consume();
        } else if (OperatingSystem.isMacOSX()
                   && OperatingSystem.isJavaVersionGreaterOrEqual("1.7")) {
          // Manage events with cmd key + special key from keyPressed because keyTyped won't be called
          keyTyped(ev);
        }
      }

      private boolean isSpaceUsedByComponent(JComponent component) {
        return component instanceof JTextComponent
            || component instanceof JComboBox;
      }

      public void keyReleased(KeyEvent ev) {
        if (ev.getKeyCode() == KeyEvent.VK_SPACE
            && previousPlanControllerMode != null) {
          controller.getPlanController().setMode(previousPlanControllerMode);
          previousPlanControllerMode = null;
          ev.consume();
        }
      }

      @Override
      public void keyTyped(KeyEvent ev) {
        char typedKey = ev.getKeyChar();
        if (typedKey != KeyEvent.CHAR_UNDEFINED) {
          // This listener manages accelerator keys that may require the use of shift key
          // depending on keyboard layout (like + - or ?)
          ActionMap actionMap = getActionMap();
          List<Action> specialKeyActions = Arrays.asList(actionMap.get(ActionType.ZOOM_IN),
              actionMap.get(ActionType.ZOOM_OUT),
              actionMap.get(ActionType.INCREASE_TEXT_SIZE),
              actionMap.get(ActionType.DECREASE_TEXT_SIZE),
              actionMap.get(ActionType.HELP));
          if (!Boolean.getBoolean("apple.laf.useScreenMenuBar")
              && Boolean.getBoolean("sweethome3d.bundle")) {
            // Manage preferences accelerator only for bundle applications without screen menu bar
            specialKeyActions = new ArrayList<Action>(specialKeyActions);
            specialKeyActions.add(actionMap.get(ActionType.PREFERENCES));
          }
          int modifiersMask = KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK | KeyEvent.META_MASK;
          if (OperatingSystem.isMacOSX()
              && ev.isMetaDown()) {
            Locale inputLocale = InputContext.getInstance().getLocale();
            if (inputLocale != null) {
              // Fix + and - typed key according to keyboards layout
              boolean shiftDown = ev.isShiftDown();
              boolean controlDown = ev.isControlDown();
              if (typedKey == '=' && shiftDown && Arrays.binarySearch(new String [] {"en", "es", "ja", "ko", "lv", "nl", "pt", "ro", "uk"}, inputLocale.getLanguage()) >= 0
                  || typedKey == '3' && shiftDown && ev.getKeyCode() != KeyEvent.VK_NUMPAD3 && Arrays.binarySearch(new String [] {"bg", "hu"}, inputLocale.getLanguage()) >= 0
                  || typedKey == '1' && !shiftDown && ev.getKeyCode() != KeyEvent.VK_NUMPAD1 && Arrays.binarySearch(new String [] {"cs", "sk"}, inputLocale.getLanguage()) >= 0
                  || typedKey == '5' && shiftDown  && ev.getKeyCode() != KeyEvent.VK_NUMPAD5 && "pl".equals(inputLocale.getLanguage())
                  || typedKey == '=' && !shiftDown && "sr".equals(inputLocale.getLanguage())
                  || (controlDown
                      && (typedKey == '=' && shiftDown && Arrays.binarySearch(new String [] {"ar", "fr", "ru", "vi", "zh"}, inputLocale.getLanguage()) >= 0
                          || typedKey == '=' && !shiftDown && Arrays.binarySearch(new String [] {"hr", "sl"}, inputLocale.getLanguage()) >= 0
                          || typedKey == 0x1d && !shiftDown && "it".equals(inputLocale.getLanguage())
                          || typedKey == '/' && shiftDown && "nl".equals(inputLocale.getLanguage())))) {
                typedKey = '+';
              } else if (controlDown
                  && (typedKey == 0x1f && Arrays.binarySearch(new String [] {"ar", "bg", "cs", "en", "es", "el", "fi", "fr", "hr", "ja", "ko", "lt", "lv", "nl", "pt", "ro", "ru", "sk", "sv", "uk", "vi", "zh"}, inputLocale.getLanguage()) >= 0
                      || typedKey == '/' && Arrays.binarySearch(new String [] {"hr", "hu", "pl", "sl", "sr"}, inputLocale.getLanguage()) >= 0
                      || typedKey == '!' && "it".equals(inputLocale.getLanguage())
                      || typedKey == '=' && "nl".equals(inputLocale.getLanguage()))) {
                typedKey = '-';
              }
            }
          }
          for (Action specialKeyAction : specialKeyActions) {
            KeyStroke actionKeyStroke = (KeyStroke)specialKeyAction.getValue(Action.ACCELERATOR_KEY);
            if (actionKeyStroke != null
                && typedKey == actionKeyStroke.getKeyChar()
                && (ev.getModifiers() & modifiersMask) == (actionKeyStroke.getModifiers() & modifiersMask)
                && specialKeyAction.isEnabled()) {
              specialKeyAction.actionPerformed(new ActionEvent(HomePane.this,
                  ActionEvent.ACTION_PERFORMED, (String)specialKeyAction.getValue(Action.ACTION_COMMAND_KEY)));
              ev.consume();
            }
          }
        }
      }
    };

  /**
   * Sets a focus traversal policy that ignores invisible split pane components.
   */
  private void updateFocusTraversalPolicy() {
    setFocusTraversalPolicy(new LayoutFocusTraversalPolicy() {
        @Override
        protected boolean accept(Component component) {
          if (super.accept(component)) {
            for (JSplitPane splitPane;
                 (splitPane = (JSplitPane)SwingUtilities.getAncestorOfClass(JSplitPane.class, component)) != null;
                 component = splitPane) {
              if (isChildComponentInvisible(splitPane, component)) {
                return false;
              }
            }
            return true;
          } else {
            return false;
          }
        }
      });
    setFocusTraversalPolicyProvider(true);
  }

  /**
   * Returns <code>true</code> if the top or the bottom component of the <code>splitPane</code>
   * is a parent of the given child component and is too small enough to show it.
   */
  private boolean isChildComponentInvisible(JSplitPane splitPane, Component childComponent) {
    return (SwingUtilities.isDescendingFrom(childComponent, splitPane.getTopComponent())
           && (splitPane.getTopComponent().getWidth() == 0
              || splitPane.getTopComponent().getHeight() == 0))
        || (SwingUtilities.isDescendingFrom(childComponent, splitPane.getBottomComponent())
           && (splitPane.getBottomComponent().getWidth() == 0
              || splitPane.getBottomComponent().getHeight() == 0));
  }

  /**
   * Returns the menu bar displayed in this pane.
   */
  private JMenuBar createMenuBar(final Home home,
                                 UserPreferences preferences,
                                 final HomeController controller) {
    // Create File menu
    JMenu fileMenu = new JMenu(this.menuActionMap.get(MenuActionType.FILE_MENU));
    addActionToMenu(ActionType.NEW_HOME, fileMenu);
    if (preferences.getHomeExamples().size() > 0) {
      addActionToMenu(ActionType.NEW_HOME_FROM_EXAMPLE, fileMenu);
    }
    addActionToMenu(ActionType.OPEN, fileMenu);


    Action openRecentHomeAction = this.menuActionMap.get(MenuActionType.OPEN_RECENT_HOME_MENU);
    if (openRecentHomeAction.getValue(Action.NAME) != null) {
      final JMenu openRecentHomeMenu = new JMenu(
          new ResourceAction.MenuItemAction(openRecentHomeAction));
      addActionToMenu(ActionType.DELETE_RECENT_HOMES, openRecentHomeMenu);
      openRecentHomeMenu.addMenuListener(new MenuListener() {
          public void menuSelected(MenuEvent ev) {
            updateOpenRecentHomeMenu(openRecentHomeMenu, controller);
          }

          public void menuCanceled(MenuEvent ev) {
          }

          public void menuDeselected(MenuEvent ev) {
          }
        });

      fileMenu.add(openRecentHomeMenu);
    }
    fileMenu.addSeparator();
    addActionToMenu(ActionType.CLOSE, fileMenu);
    addActionToMenu(ActionType.SAVE, fileMenu);
    addActionToMenu(ActionType.SAVE_AS, fileMenu);
    addActionToMenu(ActionType.SAVE_AND_COMPRESS, fileMenu);
    fileMenu.addSeparator();
    addActionToMenu(ActionType.PAGE_SETUP, fileMenu);
    addActionToMenu(ActionType.PRINT_PREVIEW, fileMenu);
    addActionToMenu(ActionType.PRINT, fileMenu);
    // Don't add PRINT_TO_PDF, PREFERENCES and EXIT menu items under Mac OS X when screen menu bar is used,
    // because PREFERENCES and EXIT items are displayed in application menu
    // and PRINT_TO_PDF is available in standard Mac OS X Print dialog
    if (!OperatingSystem.isMacOSX()) {
      addActionToMenu(ActionType.PRINT_TO_PDF, fileMenu);
    }
    if (!OperatingSystem.isMacOSX()
        || !Boolean.getBoolean("apple.laf.useScreenMenuBar")) {
      fileMenu.addSeparator();
      addActionToMenu(ActionType.PREFERENCES, fileMenu);
    }

    // Create Edit menu
    JMenu editMenu = new JMenu(this.menuActionMap.get(MenuActionType.EDIT_MENU));
    addActionToMenu(ActionType.UNDO, editMenu);
    addActionToMenu(ActionType.REDO, editMenu);
    editMenu.addSeparator();
    addActionToMenu(ActionType.CUT, editMenu);
    addActionToMenu(ActionType.COPY, editMenu);
    addActionToMenu(ActionType.PASTE, editMenu);
    addActionToMenu(ActionType.PASTE_TO_GROUP, editMenu);
    addActionToMenu(ActionType.PASTE_STYLE, editMenu);
    editMenu.addSeparator();
    addActionToMenu(ActionType.DELETE, editMenu);
    addActionToMenu(ActionType.SELECT_ALL, editMenu);
    addActionToMenu(ActionType.SELECT_ALL_AT_ALL_LEVELS, editMenu);

    // Create Furniture menu
    JMenu furnitureMenu = new JMenu(this.menuActionMap.get(MenuActionType.FURNITURE_MENU));
    addActionToMenu(ActionType.ADD_HOME_FURNITURE, furnitureMenu);
    addActionToMenu(ActionType.ADD_FURNITURE_TO_GROUP, furnitureMenu);
    addActionToMenu(ActionType.MODIFY_FURNITURE, furnitureMenu);
    addActionToMenu(ActionType.GROUP_FURNITURE, furnitureMenu);
    addActionToMenu(ActionType.UNGROUP_FURNITURE, furnitureMenu);
    furnitureMenu.addSeparator();
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_TOP, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_BOTTOM, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_LEFT, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_RIGHT, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_FRONT_SIDE, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_BACK_SIDE, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_LEFT_SIDE, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_RIGHT_SIDE, furnitureMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_SIDE_BY_SIDE, furnitureMenu);
    addActionToMenu(ActionType.DISTRIBUTE_FURNITURE_HORIZONTALLY, furnitureMenu);
    addActionToMenu(ActionType.DISTRIBUTE_FURNITURE_VERTICALLY, furnitureMenu);
    addActionToMenu(ActionType.RESET_FURNITURE_ELEVATION, furnitureMenu);
    furnitureMenu.addSeparator();
    addActionToMenu(ActionType.IMPORT_FURNITURE, furnitureMenu);
    addActionToMenu(ActionType.IMPORT_FURNITURE_LIBRARY, furnitureMenu);
    addActionToMenu(ActionType.IMPORT_TEXTURE, furnitureMenu);
    addActionToMenu(ActionType.IMPORT_TEXTURES_LIBRARY, furnitureMenu);
    furnitureMenu.addSeparator();
    furnitureMenu.add(createFurnitureSortMenu(home, preferences));
    furnitureMenu.add(createFurnitureDisplayPropertyMenu(home, preferences));
    furnitureMenu.addSeparator();
    addActionToMenu(ActionType.EXPORT_TO_CSV, furnitureMenu);

    // Create Plan menu
    JMenu planMenu = new JMenu(this.menuActionMap.get(MenuActionType.PLAN_MENU));
    addToggleActionToMenu(ActionType.SELECT, true, planMenu);
    addToggleActionToMenu(ActionType.PAN, true, planMenu);
    addToggleActionToMenu(ActionType.CREATE_WALLS, true, planMenu);
    addToggleActionToMenu(ActionType.CREATE_ROOMS, true, planMenu);
    addToggleActionToMenu(ActionType.CREATE_POLYLINES, true, planMenu);
    addToggleActionToMenu(ActionType.CREATE_DIMENSION_LINES, true, planMenu);
    addToggleActionToMenu(ActionType.CREATE_LABELS, true, planMenu);
    planMenu.addSeparator();
    JMenuItem lockUnlockBasePlanMenuItem = createLockUnlockBasePlanMenuItem(home, false);
    if (lockUnlockBasePlanMenuItem != null) {
      planMenu.add(lockUnlockBasePlanMenuItem);
    }
    addActionToMenu(ActionType.FLIP_HORIZONTALLY, planMenu);
    addActionToMenu(ActionType.FLIP_VERTICALLY, planMenu);
    addActionToMenu(ActionType.MODIFY_COMPASS, planMenu);
    addActionToMenu(ActionType.MODIFY_WALL, planMenu);
    addActionToMenu(ActionType.JOIN_WALLS, planMenu);
    addActionToMenu(ActionType.REVERSE_WALL_DIRECTION, planMenu);
    addActionToMenu(ActionType.SPLIT_WALL, planMenu);
    addActionToMenu(ActionType.MODIFY_ROOM, planMenu);
    addActionToMenu(ActionType.MODIFY_POLYLINE, planMenu);
    addActionToMenu(ActionType.MODIFY_DIMENSION_LINE, planMenu);
    addActionToMenu(ActionType.MODIFY_LABEL, planMenu);
    planMenu.add(createTextStyleMenu(home, preferences, false));
    planMenu.addSeparator();
    JMenuItem importModifyBackgroundImageMenuItem = createImportModifyBackgroundImageMenuItem(home, false);
    if (importModifyBackgroundImageMenuItem != null) {
      planMenu.add(importModifyBackgroundImageMenuItem);
    }
    JMenuItem hideShowBackgroundImageMenuItem = createHideShowBackgroundImageMenuItem(home, false);
    if (hideShowBackgroundImageMenuItem != null) {
      planMenu.add(hideShowBackgroundImageMenuItem);
    }
    addActionToMenu(ActionType.DELETE_BACKGROUND_IMAGE, planMenu);
    planMenu.addSeparator();
    planMenu.add(createLevelsMenu(home, preferences));
    planMenu.addSeparator();
    addActionToMenu(ActionType.ZOOM_IN, planMenu);
    addActionToMenu(ActionType.ZOOM_OUT, planMenu);
    planMenu.addSeparator();
    addActionToMenu(ActionType.EXPORT_TO_SVG, planMenu);

    // Create 3D Preview menu
    JMenu preview3DMenu = new JMenu(this.menuActionMap.get(MenuActionType.VIEW_3D_MENU));
    addToggleActionToMenu(ActionType.VIEW_FROM_TOP, true, preview3DMenu);
    addToggleActionToMenu(ActionType.VIEW_FROM_OBSERVER, true, preview3DMenu);
    addActionToMenu(ActionType.MODIFY_OBSERVER, preview3DMenu);
    addActionToMenu(ActionType.STORE_POINT_OF_VIEW, preview3DMenu);
    JMenu goToPointOfViewMenu = createGoToPointOfViewMenu(home, preferences, controller);
    if (goToPointOfViewMenu != null) {
      preview3DMenu.add(goToPointOfViewMenu);
    }
    addActionToMenu(ActionType.DELETE_POINTS_OF_VIEW, preview3DMenu);
    preview3DMenu.addSeparator();
    JMenuItem attachDetach3DViewMenuItem = createAttachDetach3DViewMenuItem(controller, false);
    if (attachDetach3DViewMenuItem != null) {
      preview3DMenu.add(attachDetach3DViewMenuItem);
    }
    addToggleActionToMenu(ActionType.DISPLAY_ALL_LEVELS, true, preview3DMenu);
    addToggleActionToMenu(ActionType.DISPLAY_SELECTED_LEVEL, true, preview3DMenu);
    addActionToMenu(ActionType.MODIFY_3D_ATTRIBUTES, preview3DMenu);
    preview3DMenu.addSeparator();
    addActionToMenu(ActionType.CREATE_PHOTO, preview3DMenu);
    addActionToMenu(ActionType.CREATE_PHOTOS_AT_POINTS_OF_VIEW, preview3DMenu);
    addActionToMenu(ActionType.CREATE_VIDEO, preview3DMenu);
    preview3DMenu.addSeparator();
    addActionToMenu(ActionType.EXPORT_TO_OBJ, preview3DMenu);

    // Create Help menu
    JMenu helpMenu = new JMenu(this.menuActionMap.get(MenuActionType.HELP_MENU));
    addActionToMenu(ActionType.HELP, helpMenu);
    if (!OperatingSystem.isMacOSX()
        || !Boolean.getBoolean("apple.laf.useScreenMenuBar")) {
      addActionToMenu(ActionType.ABOUT, helpMenu);
    }

    // Add menus to menu bar
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(furnitureMenu);
    if (controller.getPlanController().getView() != null) {
      menuBar.add(planMenu);
    }
    if (controller.getHomeController3D().getView() != null) {
      menuBar.add(preview3DMenu);
    }
    menuBar.add(helpMenu);

    // Add plugin actions menu items
    for (Action pluginAction : this.pluginActions) {
      String pluginMenu = (String)pluginAction.getValue(PluginAction.Property.MENU.name());
      if (pluginMenu != null) {
        boolean pluginActionAdded = false;
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
          JMenu menu = menuBar.getMenu(i);
          if (pluginMenu.equalsIgnoreCase(menu.getText())) {
            // Add menu item to existing menu
            menu.addSeparator();
            menu.add(new ResourceAction.MenuItemAction(pluginAction));
            pluginActionAdded = true;
            break;
          }
        }

        if (!pluginActionAdded) {
          // Search if menu matches an unlocalized menu name
          for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            if (menu.getAction() != null
                && pluginMenu.equalsIgnoreCase((String)menu.getAction().getValue(ResourceAction.UNLOCALIZED_NAME))) {
              menu.addSeparator();
              menu.add(new ResourceAction.MenuItemAction(pluginAction));
              pluginActionAdded = true;
              break;
            }
          }

          if (!pluginActionAdded) {
            // Create missing menu before last menu
            JMenu menu = new JMenu(pluginMenu);
            menu.add(new ResourceAction.MenuItemAction(pluginAction));
            menuBar.add(menu, menuBar.getMenuCount() - 1);
          }
        }
      }
    }

    // Add EXIT action at end to ensure it's the last item of file menu
    if (!OperatingSystem.isMacOSX()
        || !Boolean.getBoolean("apple.laf.useScreenMenuBar")) {
      fileMenu.addSeparator();
      addActionToMenu(ActionType.EXIT, fileMenu);
    }

    removeUselessSeparatorsAndEmptyMenus(menuBar);
    return menuBar;
  }

  /**
   * Adds the given action to <code>menu</code>.
   */
  private void addActionToMenu(ActionType actionType, JMenu menu) {
    addActionToMenu(actionType, false, menu);
  }

  /**
   * Adds the given action to <code>menu</code>.
   */
  private void addActionToMenu(ActionType actionType,
                               boolean popup,
                               JMenu menu) {
    Action action = getActionMap().get(actionType);
    if (action != null && action.getValue(Action.NAME) != null) {
      menu.add(popup
          ? new ResourceAction.PopupMenuItemAction(action)
          : new ResourceAction.MenuItemAction(action));
    }
  }

  /**
   * Adds to <code>menu</code> the menu item matching the given <code>actionType</code>.
   */
  private void addToggleActionToMenu(ActionType actionType,
                                     boolean radioButton,
                                     JMenu menu) {
    addToggleActionToMenu(actionType, false, radioButton, menu);
  }

  /**
   * Adds to <code>menu</code> the menu item matching the given <code>actionType</code>.
   */
  private void addToggleActionToMenu(ActionType actionType,
                                     boolean popup,
                                     boolean radioButton,
                                     JMenu menu) {
    Action action = getActionMap().get(actionType);
    if (action != null && action.getValue(Action.NAME) != null) {
      menu.add(createToggleMenuItem(action, popup, radioButton));
    }
  }

  /**
   * Creates a menu item for a toggle action.
   */
  private JMenuItem createToggleMenuItem(Action action,
                                         boolean popup,
                                         boolean radioButton) {
    JMenuItem menuItem;
    if (radioButton) {
      menuItem = new JRadioButtonMenuItem();
    } else {
      menuItem = new JCheckBoxMenuItem();
    }
    // Configure model
    menuItem.setModel((JToggleButton.ToggleButtonModel)action.getValue(ResourceAction.TOGGLE_BUTTON_MODEL));
    // Configure menu item action after setting its model to avoid losing its mnemonic
    menuItem.setAction(popup
        ? new ResourceAction.PopupMenuItemAction(action)
        : new ResourceAction.MenuItemAction(action));
    return menuItem;
  }

  /**
   * Adds the given action to <code>menu</code>.
   */
  private JMenuItem addActionToPopupMenu(ActionType actionType, JPopupMenu menu) {
    Action action = getActionMap().get(actionType);
    if (action != null && action.getValue(Action.NAME) != null) {
      menu.add(new ResourceAction.PopupMenuItemAction(action));
      return (JMenuItem)menu.getComponent(menu.getComponentCount() - 1);
    }
    return null;
  }

  /**
   * Adds to <code>menu</code> the menu item matching the given <code>actionType</code>
   * and returns <code>true</code> if it was added.
   */
  private void addToggleActionToPopupMenu(ActionType actionType,
                                          boolean radioButton,
                                          JPopupMenu menu) {
    Action action = getActionMap().get(actionType);
    if (action != null && action.getValue(Action.NAME) != null) {
      menu.add(createToggleMenuItem(action, true, radioButton));
    }
  }

  /**
   * Removes the useless separators and empty menus among children of component.
   */
  private void removeUselessSeparatorsAndEmptyMenus(JComponent component) {
    for (int i = component.getComponentCount() - 1; i >= 0; i--) {
      Component child = component.getComponent(i);
      if (child instanceof JSeparator
          && (i == component.getComponentCount() - 1
              || i > 0 && component.getComponent(i - 1) instanceof JSeparator)) {
        component.remove(i);
      } else if (child instanceof JMenu) {
        removeUselessSeparatorsAndEmptyMenus(((JMenu)child).getPopupMenu());
      }
      if (child instanceof JMenu
          && (((JMenu)child).getMenuComponentCount() == 0
              || ((JMenu)child).getMenuComponentCount() == 1
                  && ((JMenu)child).getMenuComponent(0) instanceof JSeparator)) {
        component.remove(i);
      }
    }
    // Don't let a menu start with a separator
    if (component.getComponentCount() > 0
        && component.getComponent(0) instanceof JSeparator) {
      component.remove(0);
    }
  }

  /**
   * Returns align or distribute menu.
   */
  private JMenu createAlignOrDistributeMenu(final Home home,
                                            final UserPreferences preferences,
                                            boolean popup) {
    JMenu alignOrDistributeMenu = new JMenu(this.menuActionMap.get(MenuActionType.ALIGN_OR_DISTRIBUTE_MENU));
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_TOP, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_BOTTOM, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_LEFT, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_RIGHT, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_FRONT_SIDE, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_BACK_SIDE, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_LEFT_SIDE, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_ON_RIGHT_SIDE, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.ALIGN_FURNITURE_SIDE_BY_SIDE, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.DISTRIBUTE_FURNITURE_HORIZONTALLY, popup, alignOrDistributeMenu);
    addActionToMenu(ActionType.DISTRIBUTE_FURNITURE_VERTICALLY, popup, alignOrDistributeMenu);
    return alignOrDistributeMenu;
  }

  /**
   * Returns furniture sort menu.
   */
  private JMenu createFurnitureSortMenu(final Home home, UserPreferences preferences) {
    // Create Furniture Sort submenu
    JMenu sortMenu = new JMenu(new ResourceAction.MenuItemAction(
        this.menuActionMap.get(MenuActionType.SORT_HOME_FURNITURE_MENU)));
    // Map sort furniture properties to sort actions
    Map<Object, Action> sortActions = new LinkedHashMap<Object, Action>();
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_CATALOG_ID,
        sortActions, HomePieceOfFurniture.SortableProperty.CATALOG_ID.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_NAME,
        sortActions, HomePieceOfFurniture.SortableProperty.NAME.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_DESCRIPTION,
        sortActions, HomePieceOfFurniture.SortableProperty.DESCRIPTION.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_CREATOR,
        sortActions, HomePieceOfFurniture.SortableProperty.CREATOR.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_LICENSE,
        sortActions, HomePieceOfFurniture.SortableProperty.LICENSE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_WIDTH,
        sortActions, HomePieceOfFurniture.SortableProperty.WIDTH.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_DEPTH,
        sortActions, HomePieceOfFurniture.SortableProperty.DEPTH.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_HEIGHT,
        sortActions, HomePieceOfFurniture.SortableProperty.HEIGHT.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_X,
        sortActions, HomePieceOfFurniture.SortableProperty.X.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_Y,
        sortActions, HomePieceOfFurniture.SortableProperty.Y.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_ELEVATION,
        sortActions, HomePieceOfFurniture.SortableProperty.ELEVATION.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_ANGLE,
        sortActions, HomePieceOfFurniture.SortableProperty.ANGLE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_LEVEL,
        sortActions, HomePieceOfFurniture.SortableProperty.LEVEL.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_MODEL_SIZE,
        sortActions, HomePieceOfFurniture.SortableProperty.MODEL_SIZE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_COLOR,
        sortActions, HomePieceOfFurniture.SortableProperty.COLOR.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_TEXTURE,
        sortActions, HomePieceOfFurniture.SortableProperty.TEXTURE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_MOVABILITY,
        sortActions, HomePieceOfFurniture.SortableProperty.MOVABLE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_TYPE,
        sortActions, HomePieceOfFurniture.SortableProperty.DOOR_OR_WINDOW.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_VISIBILITY,
        sortActions, HomePieceOfFurniture.SortableProperty.VISIBLE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_PRICE,
        sortActions, HomePieceOfFurniture.SortableProperty.PRICE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX_PERCENTAGE,
        sortActions, HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX_PERCENTAGE.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_VALUE_ADDED_TAX,
        sortActions, HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX.name());
    addActionToMap(ActionType.SORT_HOME_FURNITURE_BY_PRICE_VALUE_ADDED_TAX_INCLUDED,
        sortActions, HomePieceOfFurniture.SortableProperty.PRICE_VALUE_ADDED_TAX_INCLUDED.name());

    // Add radio button menu items to sub menu and make them share the same radio button group
    ButtonGroup sortButtonGroup = new ButtonGroup();
    for (Map.Entry<Object, Action> entry : sortActions.entrySet()) {
      final Object furnitureProperty = entry.getKey();
      Action sortAction = entry.getValue();
      final JRadioButtonMenuItem sortMenuItem = new JRadioButtonMenuItem();
      // Use a special model for sort radio button menu item that is selected if
      // home is sorted on furnitureProperty criterion
      sortMenuItem.setModel(new JToggleButton.ToggleButtonModel() {
          @Override
          public boolean isSelected() {
            return furnitureProperty.equals(home.getFurnitureSortedPropertyName());
          }
        });

      final Action menuItemAction = new ResourceAction.MenuItemAction(sortAction);
      // Configure check box menu item action after setting its model to avoid losing its mnemonic
      sortMenuItem.setAction(menuItemAction);

      if (sortAction instanceof ResourceAction) {
        sortMenuItem.setVisible(Boolean.TRUE.equals(sortAction.getValue(ResourceAction.VISIBLE)));
        // Add listener on action visibility to update menu item and furniture sort
        sortAction.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
              if (ResourceAction.VISIBLE.equals(ev.getPropertyName())) {
                Boolean visible = (Boolean)ev.getNewValue();
                sortMenuItem.setVisible(visible);
                if (furnitureProperty.equals(home.getFurnitureSortedPropertyName())) {
                  menuItemAction.actionPerformed(null);
                }
              }
            }
          });
      }

      sortMenu.add(sortMenuItem);
      sortButtonGroup.add(sortMenuItem);
    }
    Action sortOrderAction = getActionMap().get(ActionType.SORT_HOME_FURNITURE_BY_DESCENDING_ORDER);
    if (sortOrderAction.getValue(Action.NAME) != null) {
      sortMenu.addSeparator();
      JCheckBoxMenuItem sortOrderCheckBoxMenuItem = new JCheckBoxMenuItem();
      // Use a special model for sort order check box menu item that is selected depending on
      // home sort order property
      sortOrderCheckBoxMenuItem.setModel(new JToggleButton.ToggleButtonModel() {
          @Override
          public boolean isSelected() {
            return home.isFurnitureDescendingSorted();
          }
        });
      sortOrderCheckBoxMenuItem.setAction(new ResourceAction.MenuItemAction(sortOrderAction));
      sortMenu.add(sortOrderCheckBoxMenuItem);
    }

    updateFurnitureSortMenu(sortMenu, home);

    return sortMenu;
  }

  /**
   * Updates additional properties in furniture sort menu.
   */
  private void updateFurnitureSortMenu(JMenu sortMenu, final Home home) {
    // Remove existing menu items
    int lastRemovedMenuItem = -1;
    for (int i = sortMenu.getMenuComponentCount() - 1; i > 0; i--) {
      JComponent menuComponent = (JComponent)sortMenu.getMenuComponent(i);
      if (menuComponent instanceof JMenuItem) {
        String actionPrefix = (String)((JMenuItem)menuComponent).getAction().getValue(ResourceAction.RESOURCE_PREFIX);
        if (actionPrefix != null && actionPrefix.startsWith(SORT_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX)) {
          sortMenu.remove(menuComponent);
          lastRemovedMenuItem = i;
        }
      }
    }

    List<ObjectProperty> furnitureAdditionalProperties = home.getFurnitureAdditionalProperties();
    if (furnitureAdditionalProperties.size() > 0) {
      if (lastRemovedMenuItem == -1) {
        sortMenu.add(new JPopupMenu.Separator(), sortMenu.getMenuComponentCount() - 2);
      }
      // Add radio button menu items to sub menu and make them share the same radio button group as the other menu items
      ButtonGroup sortButtonGroup = ((DefaultButtonModel)((AbstractButton)sortMenu.getMenuComponent(0)).getModel()).getGroup();
      for (final ObjectProperty property : furnitureAdditionalProperties) {
        if (property.isDisplayable()) {
          final JRadioButtonMenuItem sortMenuItem = new JRadioButtonMenuItem();
          // Use a special model for sort radio button menu item that is selected if
          // home is sorted on furnitureProperty criterion
          sortMenuItem.setModel(new JToggleButton.ToggleButtonModel() {
            @Override
            public boolean isSelected() {
              return property.getName().equals(home.getFurnitureSortedPropertyName());
            }
          });
          // Configure check box menu item action after setting its model to avoid losing its mnemonic
          sortMenuItem.setAction(new ResourceAction.MenuItemAction(
              getActionMap().get(SORT_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX + property.getName())));
          sortMenu.add(sortMenuItem, sortMenu.getMenuComponentCount() - 2);
          sortButtonGroup.add(sortMenuItem);
        }
      }
    } else {
      // Remove separator
      sortMenu.remove(sortMenu.getMenuComponentCount() - 3);
    }
  }

  /**
   * Adds to <code>actions</code> the action matching <code>actionType</code>.
   */
  private void addActionToMap(ActionType actionType,
                              Map<Object, Action> actions,
                              Object key) {
    Action action = getActionMap().get(actionType);
    if (action != null && action.getValue(Action.NAME) != null) {
      actions.put(key, action);
    }
  }

  /**
   * Returns furniture display property menu.
   */
  private JMenu createFurnitureDisplayPropertyMenu(final Home home, UserPreferences preferences) {
    // Create Furniture Display property submenu
    JMenu displayPropertyMenu = new JMenu(new ResourceAction.MenuItemAction(
        this.menuActionMap.get(MenuActionType.DISPLAY_HOME_FURNITURE_PROPERTY_MENU)));
    // Map displayProperty furniture properties to displayProperty actions
    Map<Object, Action> displayPropertyActions = new LinkedHashMap<Object, Action>();
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_CATALOG_ID,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.CATALOG_ID.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_NAME,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.NAME.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_DESCRIPTION,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.DESCRIPTION.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_CREATOR,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.CREATOR.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_LICENSE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.LICENSE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_WIDTH,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.WIDTH.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_DEPTH,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.DEPTH.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_HEIGHT,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.HEIGHT.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_X,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.X.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_Y,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.Y.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_ELEVATION,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.ELEVATION.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_ANGLE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.ANGLE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_LEVEL,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.LEVEL.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_MODEL_SIZE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.MODEL_SIZE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_COLOR,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.COLOR.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_TEXTURE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.TEXTURE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_MOVABLE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.MOVABLE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_DOOR_OR_WINDOW,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.DOOR_OR_WINDOW.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_VISIBLE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.VISIBLE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_PRICE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.PRICE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX_PERCENTAGE,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX_PERCENTAGE.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_VALUE_ADDED_TAX,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.VALUE_ADDED_TAX.name());
    addActionToMap(ActionType.DISPLAY_HOME_FURNITURE_PRICE_VALUE_ADDED_TAX_INCLUDED,
        displayPropertyActions, HomePieceOfFurniture.SortableProperty.PRICE_VALUE_ADDED_TAX_INCLUDED.name());

    // Add check box menu items to sub menu
    for (Map.Entry<Object, Action> entry : displayPropertyActions.entrySet()) {
      final Object furnitureProperty = entry.getKey();
      final Action displayPropertyAction = entry.getValue();
      final JCheckBoxMenuItem displayPropertyMenuItem = new JCheckBoxMenuItem();
      // Use a special model for displayProperty check box menu item that is selected if
      // home furniture visible properties contains furnitureProperty
      displayPropertyMenuItem.setModel(new JToggleButton.ToggleButtonModel() {
          @Override
          public boolean isSelected() {
            return home.getFurnitureVisiblePropertyNames().contains(furnitureProperty);
          }
        });

      // Configure check box menu item action after setting its model to avoid losing its mnemonic
      displayPropertyMenuItem.setAction(displayPropertyAction);

      if (displayPropertyAction instanceof ResourceAction) {
        displayPropertyMenuItem.setVisible(Boolean.TRUE.equals(displayPropertyAction.getValue(ResourceAction.VISIBLE)));
        // Add listener on action visibility to update menu item and furniture property visibility
        displayPropertyAction.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
              if (ResourceAction.VISIBLE.equals(ev.getPropertyName())) {
                Boolean visible = (Boolean)ev.getNewValue();
                displayPropertyMenuItem.setVisible(visible);
                if (!visible
                    && home.getFurnitureVisiblePropertyNames().contains(furnitureProperty)) {
                  displayPropertyAction.actionPerformed(null);
                }
              }
            }
          });
      }

      displayPropertyMenu.add(displayPropertyMenuItem);
    }

    updateDisplayPropertyMenu(displayPropertyMenu, home);

    return displayPropertyMenu;
  }

  /**
   * Updates additional properties in display property menu.
   */
  private void updateDisplayPropertyMenu(JMenu displayPropertyMenu, final Home home) {
    // Remove existing menu items
    int lastRemovedMenuItem = -1;
    for (int i = displayPropertyMenu.getMenuComponentCount() - 1; i > 0; i--) {
      JComponent menuComponent = (JComponent)displayPropertyMenu.getMenuComponent(i);
      if (menuComponent instanceof JMenuItem) {
        String actionPrefix = (String)((JMenuItem)menuComponent).getAction().getValue(ResourceAction.RESOURCE_PREFIX);
        if (actionPrefix != null && actionPrefix.startsWith(DISPLAY_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX)) {
          displayPropertyMenu.remove(menuComponent);
          lastRemovedMenuItem = i;
        }
      }
    }

    List<ObjectProperty> furnitureAdditionalProperties = home.getFurnitureAdditionalProperties();
    if (furnitureAdditionalProperties.size() > 0) {
      if (lastRemovedMenuItem == -1) {
        displayPropertyMenu.addSeparator();
      }
      // Add check box menuitems to sub menu
      for (final ObjectProperty property : furnitureAdditionalProperties) {
        if (property.isDisplayable()) {
          final JCheckBoxMenuItem displayPropertyMenuItem = new JCheckBoxMenuItem();
          // Use a special model for sort radio button menu item that is selected if
          // home is sorted on furnitureProperty criterion
          displayPropertyMenuItem.setModel(new JToggleButton.ToggleButtonModel() {
            @Override
            public boolean isSelected() {
              return home.getFurnitureVisiblePropertyNames().contains(property.getName());
            }
          });
          // Configure check box menu item action after setting its model to avoid losing its mnemonic
          displayPropertyMenuItem.setAction(
              getActionMap().get(DISPLAY_HOME_FURNITURE_ADDITIONAL_PROPERTY_ACTION_PREFIX + property.getName()));
          displayPropertyMenu.add(displayPropertyMenuItem);
        }
      }
    } else {
      // Remove separator
      displayPropertyMenu.remove(displayPropertyMenu.getMenuComponentCount() - 1);
    }
  }

  /**
   * Returns Lock / Unlock base plan menu item.
   */
  private JMenuItem createLockUnlockBasePlanMenuItem(final Home home,
                                                       final boolean popup) {
    ActionMap actionMap = getActionMap();
    final Action unlockBasePlanAction = actionMap.get(ActionType.UNLOCK_BASE_PLAN);
    final Action lockBasePlanAction = actionMap.get(ActionType.LOCK_BASE_PLAN);
    if (unlockBasePlanAction != null
        && unlockBasePlanAction.getValue(Action.NAME) != null
        && lockBasePlanAction.getValue(Action.NAME) != null) {
      final JMenuItem lockUnlockBasePlanMenuItem = new JMenuItem(
          createLockUnlockBasePlanAction(home, popup));
      // Add a listener to home on basePlanLocked property change to
      // switch action according to basePlanLocked change
      home.addPropertyChangeListener(Home.Property.BASE_PLAN_LOCKED,
          new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
              lockUnlockBasePlanMenuItem.setAction(
                  createLockUnlockBasePlanAction(home, popup));
            }
          });
      return lockUnlockBasePlanMenuItem;
    } else {
      return null;
    }
  }

  /**
   * Returns the action active on Lock / Unlock base plan menu item.
   */
  private Action createLockUnlockBasePlanAction(Home home, boolean popup) {
    ActionType actionType = home.isBasePlanLocked()
        ? ActionType.UNLOCK_BASE_PLAN
        : ActionType.LOCK_BASE_PLAN;
    Action action = getActionMap().get(actionType);
    return popup
        ? new ResourceAction.PopupMenuItemAction(action)
        : new ResourceAction.MenuItemAction(action);
  }

  /**
   * Returns Lock / Unlock base plan button.
   */
  private JComponent createLockUnlockBasePlanButton(final Home home) {
    ActionMap actionMap = getActionMap();
    final Action unlockBasePlanAction = actionMap.get(ActionType.UNLOCK_BASE_PLAN);
    final Action lockBasePlanAction = actionMap.get(ActionType.LOCK_BASE_PLAN);
    if (unlockBasePlanAction != null
        && unlockBasePlanAction.getValue(Action.NAME) != null
        && lockBasePlanAction.getValue(Action.NAME) != null) {
      final JButton lockUnlockBasePlanButton = new JButton(
          new ResourceAction.ToolBarAction(home.isBasePlanLocked()
              ? unlockBasePlanAction
              : lockBasePlanAction));
      lockUnlockBasePlanButton.setBorderPainted(false);
      lockUnlockBasePlanButton.setContentAreaFilled(false);
      lockUnlockBasePlanButton.setFocusable(false);
      // Add a listener to home on basePlanLocked property change to
      // switch action according to basePlanLocked change
      home.addPropertyChangeListener(Home.Property.BASE_PLAN_LOCKED,
          new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
              lockUnlockBasePlanButton.setAction(
                  new ResourceAction.ToolBarAction(home.isBasePlanLocked()
                      ? unlockBasePlanAction
                      : lockBasePlanAction));
            }
          });
      return lockUnlockBasePlanButton;
    } else {
      return null;
    }
  }

  /**
   * Returns Enable / Disable magnetism button.
   */
  private JComponent createEnableDisableMagnetismButton(final UserPreferences preferences) {
    ActionMap actionMap = getActionMap();
    final Action disableMagnetismAction = actionMap.get(ActionType.DISABLE_MAGNETISM);
    final Action enableMagnetismAction = actionMap.get(ActionType.ENABLE_MAGNETISM);
    if (disableMagnetismAction != null
        && disableMagnetismAction.getValue(Action.NAME) != null
        && enableMagnetismAction.getValue(Action.NAME) != null) {
      final JButton enableDisableMagnetismButton = new JButton(
          new ResourceAction.ToolBarAction(preferences.isMagnetismEnabled()
              ? disableMagnetismAction
              : enableMagnetismAction));
      // Add a listener to preferences on magnestismEnabled property change to
      // switch action according to magnestismEnabled change
      preferences.addPropertyChangeListener(UserPreferences.Property.MAGNETISM_ENABLED,
          new MagnetismChangeListener(this, enableDisableMagnetismButton));
      return enableDisableMagnetismButton;
    } else {
      return null;
    }
  }

  /**
   * Preferences property listener bound to this component with a weak reference to avoid
   * strong link between preferences and this component.
   */
  private static class MagnetismChangeListener implements PropertyChangeListener {
    private WeakReference<HomePane> homePane;
    private WeakReference<JButton>  enableDisableMagnetismButton;

    public MagnetismChangeListener(HomePane homePane, JButton enableDisableMagnetismButton) {
      this.enableDisableMagnetismButton = new WeakReference<JButton>(enableDisableMagnetismButton);
      this.homePane = new WeakReference<HomePane>(homePane);
    }

    public void propertyChange(PropertyChangeEvent ev) {
      // If home pane was garbage collected, remove this listener from preferences
      HomePane homePane = this.homePane.get();
      JButton enableDisableMagnetismButton = this.enableDisableMagnetismButton.get();
      UserPreferences preferences = (UserPreferences)ev.getSource();
      UserPreferences.Property property = UserPreferences.Property.valueOf(ev.getPropertyName());
      if (homePane == null || enableDisableMagnetismButton == null) {
        preferences.removePropertyChangeListener(property, this);
      } else {
        enableDisableMagnetismButton.setAction(
            new ResourceAction.ToolBarAction(preferences.isMagnetismEnabled()
                ? homePane.getActionMap().get(ActionType.DISABLE_MAGNETISM)
                : homePane.getActionMap().get(ActionType.ENABLE_MAGNETISM)));
      }
    }
  }

  /**
   * Returns text style menu.
   */
  private JMenu createTextStyleMenu(final Home home,
                                    final UserPreferences preferences,
                                    boolean popup) {
    JMenu modifyTextStyleMenu = new JMenu(new ResourceAction.MenuItemAction(
        this.menuActionMap.get(MenuActionType.MODIFY_TEXT_STYLE)));

    addActionToMenu(ActionType.INCREASE_TEXT_SIZE, popup, modifyTextStyleMenu);
    addActionToMenu(ActionType.DECREASE_TEXT_SIZE, popup, modifyTextStyleMenu);
    modifyTextStyleMenu.addSeparator();
    addToggleActionToMenu(ActionType.TOGGLE_BOLD_STYLE, popup, false, modifyTextStyleMenu);
    addToggleActionToMenu(ActionType.TOGGLE_ITALIC_STYLE, popup, false, modifyTextStyleMenu);
    return modifyTextStyleMenu;
  }

  /**
   * Returns levels menu.
   */
  private JMenu createLevelsMenu(final Home home,
                                 final UserPreferences preferences) {
    JMenu levelsMenu = new JMenu(new ResourceAction.MenuItemAction(
        this.menuActionMap.get(MenuActionType.LEVELS_MENU)));

    addActionToMenu(ActionType.ADD_LEVEL, levelsMenu);
    addActionToMenu(ActionType.ADD_LEVEL_AT_SAME_ELEVATION, levelsMenu);
    JMenuItem makeLevelUnviewableViewableMenuItem = createMakeLevelUnviewableViewableMenuItem(home, false);
    if (makeLevelUnviewableViewableMenuItem != null) {
      levelsMenu.add(makeLevelUnviewableViewableMenuItem);
    }
    addActionToMenu(ActionType.MAKE_LEVEL_ONLY_VIEWABLE_ONE, levelsMenu);
    addActionToMenu(ActionType.MAKE_ALL_LEVELS_VIEWABLE, levelsMenu);
    addActionToMenu(ActionType.MODIFY_LEVEL, levelsMenu);
    addActionToMenu(ActionType.DELETE_LEVEL, levelsMenu);
    return levelsMenu;
  }

  /**
   * Creates a toggle button model that is selected when all the text of the
   * selected items in <code>home</code> use bold style.
   */
  private JToggleButton.ToggleButtonModel createBoldStyleToggleModel(final Home home,
                                                                     final UserPreferences preferences) {
    return new JToggleButton.ToggleButtonModel() {
      {
        home.addSelectionListener(new SelectionListener() {
          public void selectionChanged(SelectionEvent ev) {
            fireStateChanged();
          }
        });
      }

      @Override
      public boolean isSelected() {
        // Find if selected items are all bold or not
        Boolean selectionBoldStyle = null;
        for (Selectable item : home.getSelectedItems()) {
          Boolean bold;
          if (item instanceof Label) {
            bold = isItemTextBold(item, ((Label)item).getStyle());
          } else if (item instanceof HomePieceOfFurniture
              && ((HomePieceOfFurniture)item).isVisible()) {
            bold = isItemTextBold(item, ((HomePieceOfFurniture)item).getNameStyle());
          } else if (item instanceof Room) {
            Room room = (Room)item;
            bold = isItemTextBold(room, room.getNameStyle());
            if (bold != isItemTextBold(room, room.getAreaStyle())) {
              bold = null;
            }
          } else if (item instanceof DimensionLine) {
            bold = isItemTextBold(item, ((DimensionLine)item).getLengthStyle());
          } else {
            continue;
          }
          if (selectionBoldStyle == null) {
            selectionBoldStyle = bold;
          } else if (bold == null || !selectionBoldStyle.equals(bold)) {
            selectionBoldStyle = null;
            break;
          }
        }
        return selectionBoldStyle != null && selectionBoldStyle;
      }

      private boolean isItemTextBold(Selectable item, TextStyle textStyle) {
        if (textStyle == null) {
          textStyle = preferences.getDefaultTextStyle(item.getClass());
        }

        return textStyle.isBold();
      }
    };
  }

  /**
   * Creates a toggle button model that is selected when all the text of the
   * selected items in <code>home</code> use italic style.
   */
  private JToggleButton.ToggleButtonModel createItalicStyleToggleModel(final Home home,
                                                                       final UserPreferences preferences) {
    return new JToggleButton.ToggleButtonModel() {
      {
        home.addSelectionListener(new SelectionListener() {
          public void selectionChanged(SelectionEvent ev) {
            fireStateChanged();
          }
        });
      }

      @Override
      public boolean isSelected() {
        // Find if selected items are all italic or not
        Boolean selectionItalicStyle = null;
        for (Selectable item : home.getSelectedItems()) {
          Boolean italic;
          if (item instanceof Label) {
            italic = isItemTextItalic(item, ((Label)item).getStyle());
          } else if (item instanceof HomePieceOfFurniture
              && ((HomePieceOfFurniture)item).isVisible()) {
            italic = isItemTextItalic(item, ((HomePieceOfFurniture)item).getNameStyle());
          } else if (item instanceof Room) {
            Room room = (Room)item;
            italic = isItemTextItalic(room, room.getNameStyle());
            if (italic != isItemTextItalic(room, room.getAreaStyle())) {
              italic = null;
            }
          } else if (item instanceof DimensionLine) {
            italic = isItemTextItalic(item, ((DimensionLine)item).getLengthStyle());
          } else {
            continue;
          }
          if (selectionItalicStyle == null) {
            selectionItalicStyle = italic;
          } else if (italic == null || !selectionItalicStyle.equals(italic)) {
            selectionItalicStyle = null;
            break;
          }
        }
        return selectionItalicStyle != null && selectionItalicStyle;
      }

      private boolean isItemTextItalic(Selectable item, TextStyle textStyle) {
        if (textStyle == null) {
          textStyle = preferences.getDefaultTextStyle(item.getClass());
        }
        return textStyle.isItalic();
      }
    };
  }

  /**
   * Returns Import / Modify background image menu item.
   */
  private JMenuItem createImportModifyBackgroundImageMenuItem(final Home home,
                                                                final boolean popup) {
    ActionMap actionMap = getActionMap();
    Action importBackgroundImageAction = actionMap.get(ActionType.IMPORT_BACKGROUND_IMAGE);
    Action modifyBackgroundImageAction = actionMap.get(ActionType.MODIFY_BACKGROUND_IMAGE);
    if (importBackgroundImageAction != null
        && importBackgroundImageAction.getValue(Action.NAME) != null
        && modifyBackgroundImageAction.getValue(Action.NAME) != null) {
      final JMenuItem importModifyBackgroundImageMenuItem = new JMenuItem(
          createImportModifyBackgroundImageAction(home, popup));
      // Add a listener to home and levels on backgroundImage property change to
      // switch action according to backgroundImage change
      addBackgroundImageChangeListener(home, new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            importModifyBackgroundImageMenuItem.setAction(
                createImportModifyBackgroundImageAction(home, popup));
          }
        });
      return importModifyBackgroundImageMenuItem;
    } else {
      return null;
    }
  }

  /**
   * Adds to home and levels the given listener to follow background image changes.
   */
  private void addBackgroundImageChangeListener(final Home home, final PropertyChangeListener listener) {
    home.addPropertyChangeListener(Home.Property.BACKGROUND_IMAGE, listener);
    home.addPropertyChangeListener(Home.Property.SELECTED_LEVEL, listener);
    final PropertyChangeListener levelChangeListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent ev) {
          if (Level.Property.BACKGROUND_IMAGE.name().equals(ev.getPropertyName())) {
            listener.propertyChange(ev);
          }
        }
      };
    for (Level level : this.home.getLevels()) {
      level.addPropertyChangeListener(levelChangeListener);
    }
    this.home.addLevelsListener(new CollectionListener<Level>() {
        public void collectionChanged(CollectionEvent<Level> ev) {
          switch (ev.getType()) {
            case ADD :
              ev.getItem().addPropertyChangeListener(levelChangeListener);
              break;
            case DELETE :
              ev.getItem().removePropertyChangeListener(levelChangeListener);
              break;
          }
        }
      });
  }

  /**
   * Returns the action active on Import / Modify menu item.
   */
  private Action createImportModifyBackgroundImageAction(Home home, boolean popup) {
    BackgroundImage backgroundImage = home.getSelectedLevel() != null
        ? home.getSelectedLevel().getBackgroundImage()
        : home.getBackgroundImage();
    ActionType backgroundImageActionType = backgroundImage == null
        ? ActionType.IMPORT_BACKGROUND_IMAGE
        : ActionType.MODIFY_BACKGROUND_IMAGE;
    Action backgroundImageAction = getActionMap().get(backgroundImageActionType);
    return popup
        ? new ResourceAction.PopupMenuItemAction(backgroundImageAction)
        : new ResourceAction.MenuItemAction(backgroundImageAction);
  }

  /**
   * Returns Hide / Show background image menu item.
   */
  private JMenuItem createHideShowBackgroundImageMenuItem(final Home home,
                                                          final boolean popup) {
    ActionMap actionMap = getActionMap();
    Action hideBackgroundImageAction = actionMap.get(ActionType.HIDE_BACKGROUND_IMAGE);
    Action showBackgroundImageAction = actionMap.get(ActionType.SHOW_BACKGROUND_IMAGE);
    if (hideBackgroundImageAction != null
        && hideBackgroundImageAction.getValue(Action.NAME) != null
        && showBackgroundImageAction.getValue(Action.NAME) != null) {
      final JMenuItem hideShowBackgroundImageMenuItem = new JMenuItem(
          createHideShowBackgroundImageAction(home, popup));
      // Add a listener to home and levels on backgroundImage property change to
      // switch action according to backgroundImage change
      addBackgroundImageChangeListener(home, new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            hideShowBackgroundImageMenuItem.setAction(
                createHideShowBackgroundImageAction(home, popup));
          }
        });
      return hideShowBackgroundImageMenuItem;
    } else {
      return null;
    }
  }

  /**
   * Returns the action active on Hide / Show menu item.
   */
  private Action createHideShowBackgroundImageAction(Home home, boolean popup) {
    BackgroundImage backgroundImage = home.getSelectedLevel() != null
        ? home.getSelectedLevel().getBackgroundImage()
        : home.getBackgroundImage();
    ActionType backgroundImageActionType = backgroundImage == null || backgroundImage.isVisible()
        ? ActionType.HIDE_BACKGROUND_IMAGE
        : ActionType.SHOW_BACKGROUND_IMAGE;
    Action backgroundImageAction = getActionMap().get(backgroundImageActionType);
    return popup
        ? new ResourceAction.PopupMenuItemAction(backgroundImageAction)
        : new ResourceAction.MenuItemAction(backgroundImageAction);
  }

  /**
   * Returns Make level unviewable / viewable menu item.
   */
  private JMenuItem createMakeLevelUnviewableViewableMenuItem(final Home home,
                                                              final boolean popup) {
    ActionMap actionMap = getActionMap();
    Action makeLevelUnviewableAction = actionMap.get(ActionType.MAKE_LEVEL_UNVIEWABLE);
    Action makeLevelViewableAction = actionMap.get(ActionType.MAKE_LEVEL_VIEWABLE);
    if (makeLevelUnviewableAction != null
        && makeLevelUnviewableAction.getValue(Action.NAME) != null
        && makeLevelViewableAction.getValue(Action.NAME) != null) {
      final JMenuItem makeLevelUnviewableViewableMenuItem = new JMenuItem(
          createMakeLevelUnviewableViewableAction(home, popup));
      // Add a listener to home and selected level on viewable property change to switch action
      final PropertyChangeListener viewabilityChangeListener = new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            makeLevelUnviewableViewableMenuItem.setAction(
                createMakeLevelUnviewableViewableAction(home, popup));
          }
        };
      Level selectedLevel = home.getSelectedLevel();
      if (selectedLevel != null) {
        selectedLevel.addPropertyChangeListener(viewabilityChangeListener);
      }
      home.addPropertyChangeListener(Home.Property.SELECTED_LEVEL,
          new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
              makeLevelUnviewableViewableMenuItem.setAction(
                  createMakeLevelUnviewableViewableAction(home, popup));
              if (ev.getOldValue() != null) {
                ((Level)ev.getOldValue()).removePropertyChangeListener(viewabilityChangeListener);
              }
              if (ev.getNewValue() != null) {
                ((Level)ev.getNewValue()).addPropertyChangeListener(viewabilityChangeListener);
              }
            }
          });
      return makeLevelUnviewableViewableMenuItem;
    } else {
      return null;
    }
  }

  /**
   * Returns the action active on Make level unviewable / viewable  menu item.
   */
  private Action createMakeLevelUnviewableViewableAction(Home home, boolean popup) {
    Level selectedLevel = home.getSelectedLevel();
    ActionType levelViewabilityActionType = selectedLevel == null || selectedLevel.isViewable()
        ? ActionType.MAKE_LEVEL_UNVIEWABLE
        : ActionType.MAKE_LEVEL_VIEWABLE;
    Action levelViewabilityAction = getActionMap().get(levelViewabilityActionType);
    return popup
        ? new ResourceAction.PopupMenuItemAction(levelViewabilityAction)
        : new ResourceAction.MenuItemAction(levelViewabilityAction);
  }

  /**
   * Returns Go to point of view menu.
   */
  private JMenu createGoToPointOfViewMenu(final Home home,
                                          UserPreferences preferences,
                                          final HomeController controller) {
    Action goToPointOfViewAction = this.menuActionMap.get(MenuActionType.GO_TO_POINT_OF_VIEW);
    if (goToPointOfViewAction.getValue(Action.NAME) != null) {
      final JMenu goToPointOfViewMenu = new JMenu(
          new ResourceAction.MenuItemAction(goToPointOfViewAction));
      updateGoToPointOfViewMenu(goToPointOfViewMenu, home, controller);
      home.addPropertyChangeListener(Home.Property.STORED_CAMERAS,
          new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
              updateGoToPointOfViewMenu(goToPointOfViewMenu, home, controller);
            }
          });
      return goToPointOfViewMenu;
    } else {
      return null;
    }
  }

  /**
   * Updates Go to point of view menu items from the cameras stored in home.
   */
  private void updateGoToPointOfViewMenu(final JMenu goToPointOfViewMenu,
                                         Home home,
                                         final HomeController controller) {
    List<Camera> storedCameras = home.getStoredCameras();
    goToPointOfViewMenu.removeAll();
    if (storedCameras.isEmpty()) {
      goToPointOfViewMenu.setEnabled(false);
      goToPointOfViewMenu.add(new ResourceAction(preferences, HomePane.class, "NoStoredPointOfView", false));
    } else {
      goToPointOfViewMenu.setEnabled(true);
      for (final Camera camera : storedCameras) {
        goToPointOfViewMenu.add(
            new AbstractAction(camera.getName()) {
              public void actionPerformed(ActionEvent e) {
                controller.getHomeController3D().goToCamera(camera);
              }
            });
      }
      if (!OperatingSystem.isMacOSX()
          || !Boolean.getBoolean("apple.laf.useScreenMenuBar")) {
        // Add up and down arrows to let user scroll menu if too long
        final int menuItemHeight = goToPointOfViewMenu.getMenuComponent(0).getPreferredSize().height;
        final int fontSize = UIManager.getFont("MenuItem.font").getSize();
        final JLabel upLabel = new JLabel(new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
              // Draw a large up arrow
              Graphics2D g2d = (Graphics2D)g;
              g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
              g2d.setStroke(new BasicStroke(2));
              g2d.drawPolyline(new int [] {fontSize + 2, fontSize + (getIconWidth() - fontSize - 2) / 2, getIconWidth() - 4}, new int [] {fontSize, 6, fontSize}, 3);
            }

            public int getIconWidth() {
              return menuItemHeight * 3 / 2 + fontSize;
            }

            public int getIconHeight() {
              return menuItemHeight;
            }
          });
        upLabel.setVisible(false);

        final JLabel downLabel = new JLabel(new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
              // Draw a large down arrow
              Graphics2D g2d = (Graphics2D)g;
              g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
              g2d.setStroke(new BasicStroke(2));
              g2d.drawPolyline(new int [] {fontSize + 2, fontSize + (getIconWidth() - fontSize - 2) / 2, getIconWidth() - 4}, new int [] {6, fontSize, 6}, 3);
            }

            public int getIconWidth() {
              return menuItemHeight * 3 / 2 + fontSize;
            }

            public int getIconHeight() {
              return menuItemHeight;
            }
          });

        upLabel.addMouseListener(new MouseAdapter() {
            private Timer timer;

            @Override
            public void mouseEntered(MouseEvent ev) {
              this.timer = new Timer(100, new ActionListener() {
                  public void actionPerformed(ActionEvent ev) {
                    // Hide last visible menu item and show the first one before the visible ones
                    int i = goToPointOfViewMenu.getMenuComponentCount() - 1;
                    while (--i > 1
                           && !goToPointOfViewMenu.getMenuComponent(i).isVisible()) {
                    }
                    if (!goToPointOfViewMenu.getMenuComponent(1).isVisible()) {
                      if (!downLabel.isVisible()) {
                        downLabel.setVisible(true);
                        goToPointOfViewMenu.getMenuComponent(i--).setVisible(false);
                      }
                      goToPointOfViewMenu.getMenuComponent(i).setVisible(false);
                      int j = i;
                      while (--j > 1
                             && goToPointOfViewMenu.getMenuComponent(j).isVisible()) {
                      }
                      if (j > 0) {
                        goToPointOfViewMenu.getMenuComponent(j).setVisible(true);
                        if (j == 1) {
                          upLabel.setVisible(false);
                          goToPointOfViewMenu.getMenuComponent(i).setVisible(true);
                          ((JMenuItem)goToPointOfViewMenu.getMenuComponent(j)).setArmed(true);
                        }
                      }
                    }
                  }
                });
              this.timer.start();
            }

            @Override
            public void mouseExited(MouseEvent ev) {
              this.timer.stop();
              this.timer = null;
            }
          });
        downLabel.addMouseListener(new MouseAdapter() {
            private Timer timer;

            @Override
            public void mouseEntered(MouseEvent ev) {
              this.timer = new Timer(100, new ActionListener() {
                  public void actionPerformed(ActionEvent ev) {
                    // Hide first visible menu item and show the first one after the visible ones
                    int i = 0;
                    while (++i < goToPointOfViewMenu.getMenuComponentCount() - 1
                           && !goToPointOfViewMenu.getMenuComponent(i).isVisible()) {
                    }
                    if (!goToPointOfViewMenu.getMenuComponent(goToPointOfViewMenu.getMenuComponentCount() - 2).isVisible()) {
                      if (!upLabel.isVisible()) {
                        upLabel.setVisible(true);
                        goToPointOfViewMenu.getMenuComponent(i++).setVisible(false);
                      }
                      goToPointOfViewMenu.getMenuComponent(i).setVisible(false);
                      int j = i;
                      while (++j < goToPointOfViewMenu.getMenuComponentCount() - 1
                             && goToPointOfViewMenu.getMenuComponent(j).isVisible()) {
                      }
                      if (j < goToPointOfViewMenu.getMenuComponentCount() - 1) {
                        goToPointOfViewMenu.getMenuComponent(j).setVisible(true);
                        if (j == goToPointOfViewMenu.getMenuComponentCount() - 2) {
                          downLabel.setVisible(false);
                          goToPointOfViewMenu.getMenuComponent(i).setVisible(true);
                          ((JMenuItem)goToPointOfViewMenu.getMenuComponent(j)).setArmed(true);
                        }
                      }
                    }
                  }
                });
              this.timer.start();
            }

            @Override
            public void mouseExited(MouseEvent ev) {
              this.timer.stop();
              this.timer = null;
            }
          });

        EventQueue.invokeLater(new Runnable() {
            public void run() {
              Dimension screenSize = getToolkit().getScreenSize();
              GraphicsConfiguration configuration = getGraphicsConfiguration();              
              Insets screenInsets = configuration != null 
                  ? getToolkit().getScreenInsets(configuration)
                  : new Insets(0, 0, 0, 0);
              screenSize.height -= screenInsets.top + screenInsets.bottom;
              if (goToPointOfViewMenu.getMenuComponentCount() > screenSize.height / menuItemHeight) {
                goToPointOfViewMenu.add(upLabel, 0);
                goToPointOfViewMenu.add(downLabel);
                for (int i = screenSize.height / menuItemHeight; i < goToPointOfViewMenu.getMenuComponentCount() - 1; i++) {
                  goToPointOfViewMenu.getMenuComponent(i).setVisible(false);
                }
              }
            }
          });
      }
    }
  }

  /**
   * Returns Attach / Detach menu item for the 3D view.
   */
  private JMenuItem createAttachDetach3DViewMenuItem(final HomeController controller,
                                                     final boolean popup) {
    ActionMap actionMap = getActionMap();
    Action display3DViewInSeparateWindowAction = actionMap.get(ActionType.DETACH_3D_VIEW);
    Action display3DViewInMainWindowAction = actionMap.get(ActionType.ATTACH_3D_VIEW);
    if (display3DViewInSeparateWindowAction != null
        && display3DViewInSeparateWindowAction.getValue(Action.NAME) != null
        && display3DViewInMainWindowAction.getValue(Action.NAME) != null) {
      final JMenuItem attachDetach3DViewMenuItem = new JMenuItem(
          createAttachDetach3DViewAction(controller, popup));
      // Add a listener to 3D view to switch action when its parent changes
      JComponent view3D = (JComponent)controller.getHomeController3D().getView();
      view3D.addAncestorListener(new AncestorListener() {
          public void ancestorAdded(AncestorEvent ev) {
            attachDetach3DViewMenuItem.setAction(
                createAttachDetach3DViewAction(controller, popup));
          }

          public void ancestorRemoved(AncestorEvent ev) {
          }

          public void ancestorMoved(AncestorEvent ev) {
          }
        });
      return attachDetach3DViewMenuItem;
    } else {
      return null;
    }
  }

  /**
   * Returns the action Attach / Detach menu item.
   */
  private Action createAttachDetach3DViewAction(HomeController controller, boolean popup) {
    JRootPane view3DRootPane = SwingUtilities.getRootPane((JComponent)controller.getHomeController3D().getView());
    ActionType display3DViewActionType = view3DRootPane == this
        ? ActionType.DETACH_3D_VIEW
        : ActionType.ATTACH_3D_VIEW;
    Action attachmentAction = getActionMap().get(display3DViewActionType);
    return popup
        ? new ResourceAction.PopupMenuItemAction(attachmentAction)
        : new ResourceAction.MenuItemAction(attachmentAction);
  }

  /**
   * Updates <code>openRecentHomeMenu</code> from current recent homes in preferences.
   */
  protected void updateOpenRecentHomeMenu(JMenu openRecentHomeMenu,
                                          final HomeController controller) {
    openRecentHomeMenu.removeAll();
    for (final String homeName : controller.getRecentHomes()) {
      openRecentHomeMenu.add(
          new AbstractAction(controller.getContentManager().getPresentationName(
                  homeName, ContentManager.ContentType.SWEET_HOME_3D)) {
            public void actionPerformed(ActionEvent e) {
              controller.open(homeName);
            }
          });
    }
    if (openRecentHomeMenu.getMenuComponentCount() > 0) {
      openRecentHomeMenu.addSeparator();
    }
    addActionToMenu(ActionType.DELETE_RECENT_HOMES, openRecentHomeMenu);
  }

  /**
   * Returns the tool bar displayed in this pane.
   */
  private JToolBar createToolBar(Home home, UserPreferences preferences) {
    final JToolBar toolBar = new UnfocusableToolBar();
    addActionToToolBar(ActionType.NEW_HOME, toolBar);
    addActionToToolBar(ActionType.OPEN, toolBar);
    addActionToToolBar(ActionType.SAVE, toolBar);
    if (!OperatingSystem.isMacOSX()) {
      addActionToToolBar(ActionType.PREFERENCES, toolBar);
    }
    toolBar.addSeparator();

    int previousCount = toolBar.getComponentCount();
    addActionToToolBar(ActionType.UNDO, toolBar);
    addActionToToolBar(ActionType.REDO, toolBar);
    if (previousCount != toolBar.getComponentCount()) {
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
    }
    addActionToToolBar(ActionType.CUT, toolBar);
    addActionToToolBar(ActionType.COPY, toolBar);
    addActionToToolBar(ActionType.PASTE, toolBar);
    toolBar.addSeparator();

    addActionToToolBar(ActionType.ADD_HOME_FURNITURE, toolBar);
    toolBar.addSeparator();

    previousCount = toolBar.getComponentCount();
    addToggleActionToToolBar(ActionType.SELECT, toolBar);
    addToggleActionToToolBar(ActionType.PAN, toolBar);
    addToggleActionToToolBar(ActionType.CREATE_WALLS, toolBar);
    addToggleActionToToolBar(ActionType.CREATE_ROOMS, toolBar);
    addToggleActionToToolBar(ActionType.CREATE_POLYLINES, toolBar);
    addToggleActionToToolBar(ActionType.CREATE_DIMENSION_LINES, toolBar);
    addToggleActionToToolBar(ActionType.CREATE_LABELS, toolBar);
    if (previousCount != toolBar.getComponentCount()) {
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      previousCount = toolBar.getComponentCount();
    }

    addActionToToolBar(ActionType.INCREASE_TEXT_SIZE, toolBar);
    addActionToToolBar(ActionType.DECREASE_TEXT_SIZE, toolBar);
    addToggleActionToToolBar(ActionType.TOGGLE_BOLD_STYLE, toolBar);
    addToggleActionToToolBar(ActionType.TOGGLE_ITALIC_STYLE, toolBar);
    if (previousCount != toolBar.getComponentCount()) {
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
    }

    addActionToToolBar(ActionType.ZOOM_IN, toolBar);
    addActionToToolBar(ActionType.ZOOM_OUT, toolBar);

    if (!OperatingSystem.isMacOSX() || getToolkit().getScreenSize().width >= 1024) {
      JComponent enableDisableMagnetismButton = createEnableDisableMagnetismButton(preferences);
      if (enableDisableMagnetismButton != null) {
        toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
        toolBar.add(enableDisableMagnetismButton);
      }
    }

    toolBar.addSeparator();
    addActionToToolBar(ActionType.CREATE_PHOTO, toolBar);
    addActionToToolBar(ActionType.CREATE_VIDEO, toolBar);
    toolBar.addSeparator();

    // Add plugin actions buttons
    boolean pluginActionsAdded = false;
    for (Action pluginAction : this.pluginActions) {
      if (Boolean.TRUE.equals(pluginAction.getValue(PluginAction.Property.TOOL_BAR.name()))) {
        addActionToToolBar(new ResourceAction.ToolBarAction(pluginAction), toolBar);
        pluginActionsAdded = true;
      }
    }
    if (pluginActionsAdded) {
      toolBar.addSeparator();
    }

    addActionToToolBar(ActionType.HELP, toolBar);

    // Remove useless separators
    for (int i = toolBar.getComponentCount() - 1; i > 0; i--) {
      Component child = toolBar.getComponent(i);
      if (child instanceof JSeparator
          && (i == toolBar.getComponentCount() - 1
              || i > 0 && toolBar.getComponent(i - 1) instanceof JSeparator)) {
        toolBar.remove(i);
      }
    }

    if (OperatingSystem.isMacOSXLeopardOrSuperior() && OperatingSystem.isJavaVersionBetween("1.7", "1.7.0_40")) {
      // Reduce tool bar height to balance segmented buttons with higher insets
      toolBar.setPreferredSize(new Dimension(0, toolBar.getPreferredSize().height - 4));
    }

    return toolBar;
  }

  /**
   * Adds to tool bar the button matching the given <code>actionType</code>
   * and returns <code>true</code> if it was added.
   */
  private void addToggleActionToToolBar(ActionType actionType,
                                        JToolBar toolBar) {
    Action action = getActionMap().get(actionType);
    if (action!= null && action.getValue(Action.NAME) != null) {
      Action toolBarAction = new ResourceAction.ToolBarAction(action);
      JToggleButton toggleButton;
      if (OperatingSystem.isMacOSXLeopardOrSuperior() && OperatingSystem.isJavaVersionBetween("1.7", "1.7.0_40")) {
        // Use higher insets to ensure the top and bottom of segmented buttons are correctly drawn
        toggleButton = new JToggleButton(toolBarAction) {
            @Override
            public Insets getInsets() {
              Insets insets = super.getInsets();
              insets.top += 3;
              insets.bottom += 3;
              return insets;
            }
          };
      } else {
        toggleButton = new JToggleButton(toolBarAction);
      }
      toggleButton.setModel((JToggleButton.ToggleButtonModel)action.getValue(ResourceAction.TOGGLE_BUTTON_MODEL));
      toolBar.add(toggleButton);
    }
  }

  /**
   * Adds to tool bar the button matching the given <code>actionType</code>.
   */
  private void addActionToToolBar(ActionType actionType,
                                  JToolBar toolBar) {
    Action action = getActionMap().get(actionType);
    if (action!= null && action.getValue(Action.NAME) != null) {
      addActionToToolBar(new ResourceAction.ToolBarAction(action), toolBar);
    }
  }

  /**
   * Adds to tool bar the button matching the given <code>action</code>.
   */
  private void addActionToToolBar(Action action,
                                  JToolBar toolBar) {
    if (OperatingSystem.isMacOSXLeopardOrSuperior() && OperatingSystem.isJavaVersionBetween("1.7", "1.7.0_40")) {
      // Add a button with higher insets to ensure the top and bottom of segmented buttons are correctly drawn
      toolBar.add(new JButton(new ResourceAction.ToolBarAction(action)) {
          @Override
          public Insets getInsets() {
            Insets insets = super.getInsets();
            insets.top += 3;
            insets.bottom += 3;
            return insets;
          }
        });
    } else {
      toolBar.add(new JButton(new ResourceAction.ToolBarAction(action)));
    }
  }

  /**
   * Enables or disables the action matching <code>actionType</code>.
   */
  public void setEnabled(ActionType actionType,
                         boolean enabled) {
    Action action = getActionMap().get(actionType);
    if (action != null) {
      action.setEnabled(enabled);
    }
  }

  /**
   * Enables or disables the action matching <code>actionKey</code>.
   */
  public void setActionEnabled(String actionKey,
                               boolean enabled) {
    Action action = getActionMap().get(actionKey);
    if (action != null) {
      action.setEnabled(enabled);
    }
  }

  /**
   * Sets the <code>NAME</code> and <code>SHORT_DESCRIPTION</code> properties value
   * of undo and redo actions. If a parameter is null,
   * the properties will be reset to their initial values.
   */
  public void setUndoRedoName(String undoText, String redoText) {
    setNameAndShortDescription(ActionType.UNDO, undoText);
    setNameAndShortDescription(ActionType.REDO, redoText);
  }

  /**
   * Sets the <code>NAME</code> and <code>SHORT_DESCRIPTION</code> properties value
   * matching <code>actionType</code>. If <code>name</code> is null,
   * the properties will be reset to their initial values.
   */
  private void setNameAndShortDescription(ActionType actionType, String name) {
    Action action = getActionMap().get(actionType);
    if (action != null) {
      if (name == null) {
        name = (String)action.getValue(Action.DEFAULT);
      }
      action.putValue(Action.NAME, name);
      action.putValue(Action.SHORT_DESCRIPTION, name);
    }
  }

  /**
   * Enables or disables transfer between components.
   */
  public void setTransferEnabled(boolean enabled) {
    boolean dragAndDropWithTransferHandlerSupported;
    try {
      // Don't use transfer handlers for drag and drop with Plugin2 under Mac OS X or when in an unsigned applet
      dragAndDropWithTransferHandlerSupported = !Boolean.getBoolean("com.eteks.sweethome3d.dragAndDropWithoutTransferHandler");
    } catch (AccessControlException ex) {
      dragAndDropWithTransferHandlerSupported = false;
    }

    JComponent catalogView = (JComponent)this.controller.getFurnitureCatalogController().getView();
    JComponent furnitureView = (JComponent)this.controller.getFurnitureController().getView();
    JComponent planView = (JComponent)this.controller.getPlanController().getView();
    JComponent view3D = (JComponent)this.controller.getHomeController3D().getView();
    if (enabled) {
      if (catalogView != null) {
        catalogView.setTransferHandler(this.catalogTransferHandler);
      }
      if (furnitureView != null) {
        furnitureView.setTransferHandler(this.furnitureTransferHandler);
        if (furnitureView instanceof Scrollable) {
          ((JViewport)furnitureView.getParent()).setTransferHandler(this.furnitureTransferHandler);
        }
      }
      if (planView != null) {
        planView.setTransferHandler(this.planTransferHandler);
      }
      if (view3D != null && this.preferences.isEditingIn3DViewEnabled()) {
        view3D.setTransferHandler(this.view3DTransferHandler);
      }
      if (!dragAndDropWithTransferHandlerSupported) {
        if (catalogView != null) {
          // Check if furniture catalog is handled by a subcomponent
          List<JViewport> viewports = SwingTools.findChildren(catalogView, JViewport.class);
          JComponent catalogComponent;
          if (viewports.size() > 0) {
            catalogComponent = (JComponent)viewports.get(0).getView();
          } else {
            catalogComponent = catalogView;
          }
          if (this.furnitureCatalogDragAndDropListener == null) {
            this.furnitureCatalogDragAndDropListener = createFurnitureCatalogMouseListener();
          }
          catalogComponent.addMouseListener(this.furnitureCatalogDragAndDropListener);
          catalogComponent.addMouseMotionListener(this.furnitureCatalogDragAndDropListener);
        }
      }
    } else {
      if (catalogView != null) {
        catalogView.setTransferHandler(null);
      }
      if (furnitureView != null) {
        furnitureView.setTransferHandler(null);
        if (furnitureView instanceof Scrollable) {
          ((JViewport)furnitureView.getParent()).setTransferHandler(null);
        }
      }
      if (planView != null) {
        planView.setTransferHandler(null);
      }
      if (view3D != null ) {
        view3D.setTransferHandler(null);
      }
      if (!dragAndDropWithTransferHandlerSupported) {
        if (catalogView != null) {
          List<JViewport> viewports = SwingTools.findChildren(catalogView, JViewport.class);
          JComponent catalogComponent;
          if (viewports.size() > 0) {
            catalogComponent = (JComponent)viewports.get(0).getView();
          } else {
            catalogComponent = catalogView;
          }
          catalogComponent.removeMouseListener(this.furnitureCatalogDragAndDropListener);
          catalogComponent.removeMouseMotionListener(this.furnitureCatalogDragAndDropListener);
        }
      }
    }
    this.transferHandlerEnabled = enabled;
  }

  /**
   * Returns a mouse listener for catalog that acts as catalog view, furniture view and plan transfer handlers
   * for drag and drop operations.
   */
  private MouseInputAdapter createFurnitureCatalogMouseListener() {
    return new MouseInputAdapter() {
        private CatalogPieceOfFurniture selectedPiece;
        private TransferHandler         transferHandler;
        private boolean                 autoscrolls;
        private Cursor                  previousCursor;
        private View                    previousView;
        private boolean                 escaped;

        {
          getActionMap().put("EscapeDragFromFurnitureCatalog", new AbstractAction() {
              public void actionPerformed(ActionEvent ev) {
                if (!escaped) {
                  if (previousView != null) {
                    if (previousView == controller.getPlanController().getView()) {
                      controller.getPlanController().stopDraggedItems();
                    }
                    if (previousCursor != null) {
                      JComponent component = (JComponent)previousView;
                      component.setCursor(previousCursor);
                      if (component.getParent() instanceof JViewport) {
                        component.getParent().setCursor(previousCursor);
                      }
                    }
                  }
                  escaped = true;
                }
              }
            });
        }

        @Override
        public void mousePressed(MouseEvent ev) {
          if (SwingUtilities.isLeftMouseButton(ev)) {
            List<CatalogPieceOfFurniture> selectedFurniture = controller.getFurnitureCatalogController().getSelectedFurniture();
            if (selectedFurniture.size() > 0) {
              JComponent source = (JComponent)ev.getSource();
              this.transferHandler = source.getTransferHandler();
              source.setTransferHandler(null);
              this.autoscrolls = source.getAutoscrolls();
              source.setAutoscrolls(false);
              this.selectedPiece = selectedFurniture.get(0);
              this.previousCursor = null;
              this.previousView = null;
              this.escaped = false;
              InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
              inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "EscapeDragFromFurnitureCatalog");
              setInputMap(WHEN_IN_FOCUSED_WINDOW, inputMap);
            }
          }
        }

        @Override
        public void mouseDragged(MouseEvent ev) {
          if (SwingUtilities.isLeftMouseButton(ev)
              && this.selectedPiece != null) {
            // Force selection again
            List<CatalogPieceOfFurniture> emptyList = Collections.emptyList();
            controller.getFurnitureCatalogController().setSelectedFurniture(emptyList);
            controller.getFurnitureCatalogController().setSelectedFurniture(Arrays.asList(new CatalogPieceOfFurniture [] {this.selectedPiece}));

            Level selectedLevel = home.getSelectedLevel();
            if (selectedLevel == null || selectedLevel.isViewable()) {
              List<Selectable> transferredFurniture = Arrays.asList(
                  new Selectable [] {controller.getFurnitureController().createHomePieceOfFurniture(this.selectedPiece)});
              View view;
              float [] pointInView = getPointInPlanView(ev, transferredFurniture);
              if (pointInView != null) {
                view = controller.getPlanController().getView();
              } else {
                view = controller.getFurnitureController().getView();
                pointInView = getPointInFurnitureView(ev);
              }

              if (this.previousView != view) {
                if (this.previousView != null) {
                  if (this.previousView == controller.getPlanController().getView()
                      && !this.escaped) {
                    controller.getPlanController().stopDraggedItems();
                  }
                  JComponent component = (JComponent)this.previousView;
                  component.setCursor(this.previousCursor);
                  if (component.getParent() instanceof JViewport) {
                    component.setCursor(this.previousCursor);
                  }
                  this.previousCursor = null;
                  this.previousView = null;
                }
                if (view != null) {
                  JComponent component = (JComponent)view;
                  this.previousCursor = component.getCursor();
                  this.previousView = view;
                  if (!escaped) {
                    component.setCursor(DragSource.DefaultCopyDrop);
                    if (component.getParent() instanceof JViewport) {
                      ((JViewport)component.getParent()).setCursor(DragSource.DefaultCopyDrop);
                    }
                    if (view == controller.getPlanController().getView()) {
                      controller.getPlanController().startDraggedItems(transferredFurniture, pointInView [0], pointInView [1]);
                    }
                  }
                }
              } else if (pointInView != null) {
                controller.getPlanController().moveMouse(pointInView [0], pointInView [1]);
              }
            }
          }
        }

        private float [] getPointInPlanView(MouseEvent ev, List<Selectable> transferredFurniture) {
          PlanView planView = controller.getPlanController().getView();
          if (planView != null) {
            JComponent planComponent = (JComponent)planView;
            Point pointInPlanComponent = SwingUtilities.convertPoint(ev.getComponent(), ev.getPoint(), planComponent);
            if (planComponent.getParent() instanceof JViewport
                    && ((JViewport)planComponent.getParent()).contains(
                        SwingUtilities.convertPoint(ev.getComponent(), ev.getPoint(), planComponent.getParent()))
                || !(planComponent.getParent() instanceof JViewport)
                    && planView.canImportDraggedItems(transferredFurniture, pointInPlanComponent.x, pointInPlanComponent.y)) {
              return new float [] {planView.convertXPixelToModel(pointInPlanComponent.x), planView.convertYPixelToModel(pointInPlanComponent.y)};
            }
          }
          return null;
        }

        private float [] getPointInFurnitureView(MouseEvent ev) {
          View furnitureView = controller.getFurnitureController().getView();
          if (furnitureView != null) {
            JComponent furnitureComponent = (JComponent)furnitureView;
            Point point = SwingUtilities.convertPoint(ev.getComponent(), ev.getX(), ev.getY(),
                furnitureComponent.getParent() instanceof JViewport
                   ? furnitureComponent.getParent()
                   : furnitureComponent);
            if (furnitureComponent.getParent() instanceof JViewport
                    && ((JViewport)furnitureComponent.getParent()).contains(point)
                || !(furnitureComponent.getParent() instanceof JViewport)
                    && furnitureComponent.contains(point)) {
              return new float [] {0, 0};
            }
          }
          return null;
        }

        @Override
        public void mouseReleased(MouseEvent ev) {
          if (SwingUtilities.isLeftMouseButton(ev)) {
            if (this.selectedPiece != null) {
              if (!this.escaped) {
                Level selectedLevel = home.getSelectedLevel();
                if (selectedLevel == null || selectedLevel.isViewable()) {
                  List<Selectable> transferredFurniture = Arrays.asList(
                          new Selectable [] {controller.getFurnitureController().createHomePieceOfFurniture(this.selectedPiece)});
                  View view;
                  float [] pointInView = getPointInPlanView(ev, transferredFurniture);
                  if (pointInView != null) {
                    controller.getPlanController().stopDraggedItems();
                    view = controller.getPlanController().getView();
                  } else {
                    view = controller.getFurnitureController().getView();
                    pointInView = getPointInFurnitureView(ev);
                  }
                  if (pointInView != null) {
                    controller.drop(transferredFurniture, view, pointInView [0], pointInView [1]);
                    JComponent component = (JComponent)this.previousView;
                    component.setCursor(this.previousCursor);
                    if (component.getParent() instanceof JViewport) {
                      component.getParent().setCursor(this.previousCursor);
                    }
                  }
                  this.selectedPiece = null;
                }
              }

              JComponent source = (JComponent)ev.getSource();
              source.setTransferHandler(this.transferHandler);
              source.setAutoscrolls(this.autoscrolls);
              InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
              inputMap.remove(KeyStroke.getKeyStroke("ESCAPE"));
              setInputMap(WHEN_IN_FOCUSED_WINDOW, inputMap);
            }
          }
        }
      };
  }

  /**
   * Returns the main pane with catalog tree, furniture table and plan pane.
   */
  private JComponent createMainPane(Home home, UserPreferences preferences,
                                    HomeController controller) {
    final JComponent catalogFurniturePane = createCatalogFurniturePane(home, preferences, controller);
    final JComponent planView3DPane = createPlanView3DPane(home, preferences, controller);

    if (catalogFurniturePane == null) {
      return planView3DPane;
    } else if (planView3DPane == null) {
      return catalogFurniturePane;
    } else {
      boolean leftToRightOrientation = ComponentOrientation.getOrientation(Locale.getDefault()).isLeftToRight();
      final JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
          leftToRightOrientation ? catalogFurniturePane  : planView3DPane,
          leftToRightOrientation ? planView3DPane  : catalogFurniturePane);
      // Set default divider location
      mainPane.setDividerLocation((int)((leftToRightOrientation ? 360 : 670) * SwingTools.getResolutionScale()));
      configureSplitPane(mainPane, home, MAIN_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY,
          leftToRightOrientation ? 0.3 : 0.7,
          true, controller);
      mainPane.addPropertyChangeListener("componentOrientation", new PropertyChangeListener () {
          public void propertyChange(PropertyChangeEvent ev) {
            if (mainPane.getComponentOrientation().isLeftToRight()) {
              mainPane.setRightComponent(null); // Needed to avoid twice the same child component
              mainPane.setLeftComponent(catalogFurniturePane);
              mainPane.setRightComponent(planView3DPane);
            } else {
              mainPane.setRightComponent(null);
              mainPane.setLeftComponent(planView3DPane);
              mainPane.setRightComponent(catalogFurniturePane);
            }
            if (mainPane.isShowing()) {
              mainPane.setDividerLocation(mainPane.getWidth() - mainPane.getDividerLocation());
            }
          }
        });
      return mainPane;
    }
  }

  /**
   * Configures <code>splitPane</code> divider location.
   * If <code>dividerLocationProperty</code> visual property exists in <code>home</code>,
   * its value will be used, otherwise the given resize weight will be used.
   */
  private void configureSplitPane(final JSplitPane splitPane,
                                  Home home,
                                  final String dividerLocationProperty,
                                  final double defaultResizeWeight,
                                  boolean showBorder,
                                  final HomeController controller) {
    splitPane.setContinuousLayout(true);
    splitPane.setOneTouchExpandable(true);
    splitPane.setResizeWeight(defaultResizeWeight);
    if (!showBorder) {
      splitPane.setBorder(null);
    }
    // Add a listener to the divider that will keep invisible components hidden when the split pane is resized
    final PropertyChangeListener resizeWeightUpdater = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent ev) {
          if (splitPane.getDividerLocation() <= 0) {
            splitPane.setResizeWeight(0);
          } else if (splitPane.getDividerLocation() + splitPane.getDividerSize() >=
              (splitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT
                  ? splitPane.getWidth() - splitPane.getInsets().left
                  : splitPane.getHeight() - splitPane.getInsets().top)) {
            splitPane.setResizeWeight(1);
          } else {
            splitPane.setResizeWeight(defaultResizeWeight);
          }
        }
      };
    splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, resizeWeightUpdater);

    // Restore divider location previously set
    Number dividerLocation = home.getNumericProperty(dividerLocationProperty);
    if (dividerLocation != null) {
      splitPane.setDividerLocation(dividerLocation.intValue());
      // Update resize weight once split pane location is set
      splitPane.addAncestorListener(new AncestorListener() {
          public void ancestorAdded(AncestorEvent ev) {
            resizeWeightUpdater.propertyChange(null);
            splitPane.removeAncestorListener(this);
          }

          public void ancestorRemoved(AncestorEvent ev) {
          }

          public void ancestorMoved(AncestorEvent ev) {
          }
        });
    }
    splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
        new PropertyChangeListener() {
          public void propertyChange(final PropertyChangeEvent ev) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                  Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                  if (focusOwner != null && isChildComponentInvisible(splitPane, focusOwner)) {
                    FocusTraversalPolicy focusTraversalPolicy = getFocusTraversalPolicy();
                    Component focusedComponent = focusTraversalPolicy.getComponentAfter(HomePane.this, focusOwner);
                    if (focusedComponent == null) {
                      focusedComponent = focusTraversalPolicy.getComponentBefore(HomePane.this, focusOwner);
                    }
                    if (focusedComponent != null) {
                      focusedComponent.requestFocusInWindow();
                    }
                  }
                  controller.setHomeProperty(dividerLocationProperty, String.valueOf(ev.getNewValue()));
                }
              });
          }
        });
  }

  /**
   * Returns the catalog tree and furniture table pane.
   */
  private JComponent createCatalogFurniturePane(Home home,
                                                UserPreferences preferences,
                                                final HomeController controller) {
    JComponent catalogView = (JComponent)controller.getFurnitureCatalogController().getView();
    if (catalogView != null) {
      // Create catalog view popup menu
      JPopupMenu catalogViewPopup = new JPopupMenu();
      addActionToPopupMenu(ActionType.COPY, catalogViewPopup);
      catalogViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.DELETE, catalogViewPopup);
      catalogViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.ADD_HOME_FURNITURE, catalogViewPopup);
      addActionToPopupMenu(ActionType.ADD_FURNITURE_TO_GROUP, catalogViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_FURNITURE, catalogViewPopup);
      catalogViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.IMPORT_FURNITURE, catalogViewPopup);
      SwingTools.hideDisabledMenuItems(catalogViewPopup);
      catalogView.setComponentPopupMenu(catalogViewPopup);

      preferences.addPropertyChangeListener(UserPreferences.Property.FURNITURE_CATALOG_VIEWED_IN_TREE,
          new FurnitureCatalogViewChangeListener(this, catalogView));
      if (catalogView instanceof Scrollable) {
        catalogView = SwingTools.createScrollPane(catalogView);
      }
    }

    // Configure furniture view
    JComponent furnitureView = (JComponent)controller.getFurnitureController().getView();
    if (furnitureView != null) {
      // Create furniture view popup menu
      JPopupMenu furnitureViewPopup = new JPopupMenu();
      addActionToPopupMenu(ActionType.UNDO, furnitureViewPopup);
      addActionToPopupMenu(ActionType.REDO, furnitureViewPopup);
      furnitureViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.CUT, furnitureViewPopup);
      addActionToPopupMenu(ActionType.COPY, furnitureViewPopup);
      addActionToPopupMenu(ActionType.PASTE, furnitureViewPopup);
      addActionToPopupMenu(ActionType.PASTE_TO_GROUP, furnitureViewPopup);
      addActionToPopupMenu(ActionType.PASTE_STYLE, furnitureViewPopup);
      furnitureViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.DELETE, furnitureViewPopup);
      addActionToPopupMenu(ActionType.SELECT_ALL, furnitureViewPopup);
      furnitureViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.MODIFY_FURNITURE, furnitureViewPopup);
      addActionToPopupMenu(ActionType.GROUP_FURNITURE, furnitureViewPopup);
      addActionToPopupMenu(ActionType.UNGROUP_FURNITURE, furnitureViewPopup);
      furnitureViewPopup.add(createAlignOrDistributeMenu(home, preferences, true));
      addActionToPopupMenu(ActionType.RESET_FURNITURE_ELEVATION, furnitureViewPopup);
      furnitureViewPopup.addSeparator();
      furnitureViewPopup.add(createFurnitureSortMenu(home, preferences));
      furnitureViewPopup.add(createFurnitureDisplayPropertyMenu(home, preferences));
      furnitureViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.EXPORT_TO_CSV, furnitureViewPopup);
      SwingTools.hideDisabledMenuItems(furnitureViewPopup);
      furnitureView.setComponentPopupMenu(furnitureViewPopup);

      if (furnitureView instanceof Scrollable) {
        // Set default traversal keys of furniture view
        KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        furnitureView.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
            focusManager.getDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        furnitureView.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
            focusManager.getDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));

        JScrollPane furnitureScrollPane = SwingTools.createScrollPane(furnitureView);
        // Add a mouse listener that gives focus to furniture view when
        // user clicks in its viewport (tables don't spread vertically if their row count is too small)
        final JViewport viewport = furnitureScrollPane.getViewport();
        viewport.addMouseListener(
            new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent ev) {
                viewport.getView().requestFocusInWindow();
              }
            });
        Number viewportY = home.getNumericProperty(FURNITURE_VIEWPORT_Y_VISUAL_PROPERTY);
        if (viewportY != null) {
          viewport.setViewPosition(new Point(0, viewportY.intValue()));
        }
        viewport.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ev) {
              controller.setHomeProperty(FURNITURE_VIEWPORT_Y_VISUAL_PROPERTY, String.valueOf(viewport.getViewPosition().y));
            }
          });
        ((JViewport)furnitureView.getParent()).setComponentPopupMenu(furnitureViewPopup);

        if (OperatingSystem.isMacOSXHighSierraOrSuperior()
            && !OperatingSystem.isJavaVersionGreaterOrEqual("1.7")) {
          // Add missing repaint calls on viewport when scroll bar is moved
          furnitureScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
              public void adjustmentValueChanged(AdjustmentEvent ev) {
                viewport.repaint();
              }
            });
        }
        furnitureView = furnitureScrollPane;
      }
    }

    if (catalogView == null) {
      return furnitureView;
    } else if (furnitureView == null) {
      return catalogView;
    } else {
      // Create a split pane that displays both components
      JSplitPane catalogFurniturePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
          catalogView, furnitureView);
      catalogFurniturePane.setBorder(null);
      catalogFurniturePane.setMinimumSize(new Dimension());
      configureSplitPane(catalogFurniturePane, home,
          CATALOG_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY, 0.5, false, controller);
      return catalogFurniturePane;
    }
  }

  /**
   * Preferences property listener bound to this component with a weak reference to avoid
   * strong link between preferences and this component.
   */
  private static class FurnitureCatalogViewChangeListener implements PropertyChangeListener {
    private WeakReference<HomePane>   homePane;
    private WeakReference<JComponent> furnitureCatalogView;

    public FurnitureCatalogViewChangeListener(HomePane homePane, JComponent furnitureCatalogView) {
      this.homePane = new WeakReference<HomePane>(homePane);
      this.furnitureCatalogView = new WeakReference<JComponent>(furnitureCatalogView);
    }

    public void propertyChange(PropertyChangeEvent ev) {
      // If home pane was garbage collected, remove this listener from preferences
      HomePane homePane = this.homePane.get();
      if (homePane == null) {
        ((UserPreferences)ev.getSource()).removePropertyChangeListener(
            UserPreferences.Property.FURNITURE_CATALOG_VIEWED_IN_TREE, this);
      } else {
        // Replace previous furniture catalog view by the new one
        JComponent oldFurnitureCatalogView = this.furnitureCatalogView.get();
        if (oldFurnitureCatalogView != null) {
          boolean transferHandlerEnabled = homePane.transferHandlerEnabled;
          homePane.setTransferEnabled(false);
          JComponent newFurnitureCatalogView = (JComponent)homePane.controller.getFurnitureCatalogController().getView();
          newFurnitureCatalogView.setComponentPopupMenu(oldFurnitureCatalogView.getComponentPopupMenu());
          homePane.setTransferEnabled(transferHandlerEnabled);
          JComponent splitPaneTopComponent = newFurnitureCatalogView;
          if (newFurnitureCatalogView instanceof Scrollable) {
            splitPaneTopComponent = SwingTools.createScrollPane(newFurnitureCatalogView);
          } else {
            splitPaneTopComponent = newFurnitureCatalogView;
          }
          ((JSplitPane)SwingUtilities.getAncestorOfClass(JSplitPane.class, oldFurnitureCatalogView)).
              setTopComponent(splitPaneTopComponent);
          newFurnitureCatalogView.applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
          this.furnitureCatalogView = new WeakReference<JComponent>(newFurnitureCatalogView);
        }
      }
    }
  }

  /**
   * Returns the plan view and 3D view pane.
   */
  private JComponent createPlanView3DPane(final Home home, UserPreferences preferences,
                                          final HomeController controller) {
    JComponent planView = (JComponent)controller.getPlanController().getView();
    if (planView != null) {
      // Create plan view popup menu
      JPopupMenu planViewPopup = new JPopupMenu();
      addActionToPopupMenu(ActionType.UNDO, planViewPopup);
      addActionToPopupMenu(ActionType.REDO, planViewPopup);
      planViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.CUT, planViewPopup);
      addActionToPopupMenu(ActionType.COPY, planViewPopup);
      addActionToPopupMenu(ActionType.PASTE, planViewPopup);
      addActionToPopupMenu(ActionType.PASTE_STYLE, planViewPopup);
      planViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.DELETE, planViewPopup);
      Action selectObjectAction = this.menuActionMap.get(MenuActionType.SELECT_OBJECT_MENU);
      final JMenu selectObjectMenu;
      if (selectObjectAction.getValue(Action.NAME) != null) {
        selectObjectMenu = new JMenu(selectObjectAction);
        planViewPopup.add(selectObjectMenu);
        Action toggleObjectSelectionAction = this.menuActionMap.get(MenuActionType.TOGGLE_SELECTION_MENU);
        if (toggleObjectSelectionAction.getValue(Action.NAME) != null) {
          // Change "Select object" menu to "Toggle object selection" when shift key is pressed
          final KeyEventDispatcher shiftKeyListener = new KeyEventDispatcher() {
              public boolean dispatchKeyEvent(KeyEvent ev) {
                selectObjectMenu.setAction(menuActionMap.get(ev.isShiftDown()
                    ? MenuActionType.TOGGLE_SELECTION_MENU
                    : MenuActionType.SELECT_OBJECT_MENU));
                return false;
              }
            };
          addAncestorListener(new AncestorListener() {
              public void ancestorAdded(AncestorEvent event) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(shiftKeyListener);
              }
              public void ancestorRemoved(AncestorEvent event) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(shiftKeyListener);
              }

              public void ancestorMoved(AncestorEvent event) {
              }
            });
        }
      } else {
        selectObjectMenu = null;
      }
      addActionToPopupMenu(ActionType.SELECT_ALL, planViewPopup);
      addActionToPopupMenu(ActionType.SELECT_ALL_AT_ALL_LEVELS, planViewPopup);
      planViewPopup.addSeparator();
      addToggleActionToPopupMenu(ActionType.SELECT, true, planViewPopup);
      addToggleActionToPopupMenu(ActionType.PAN, true, planViewPopup);
      addToggleActionToPopupMenu(ActionType.CREATE_WALLS, true, planViewPopup);
      addToggleActionToPopupMenu(ActionType.CREATE_ROOMS, true, planViewPopup);
      addToggleActionToPopupMenu(ActionType.CREATE_POLYLINES, true, planViewPopup);
      addToggleActionToPopupMenu(ActionType.CREATE_DIMENSION_LINES, true, planViewPopup);
      addToggleActionToPopupMenu(ActionType.CREATE_LABELS, true, planViewPopup);
      planViewPopup.addSeparator();
      JMenuItem lockUnlockBasePlanMenuItem = createLockUnlockBasePlanMenuItem(home, true);
      if (lockUnlockBasePlanMenuItem != null) {
        planViewPopup.add(lockUnlockBasePlanMenuItem);
      }
      addActionToPopupMenu(ActionType.FLIP_HORIZONTALLY, planViewPopup);
      addActionToPopupMenu(ActionType.FLIP_VERTICALLY, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_FURNITURE, planViewPopup);
      addActionToPopupMenu(ActionType.GROUP_FURNITURE, planViewPopup);
      addActionToPopupMenu(ActionType.UNGROUP_FURNITURE, planViewPopup);
      planViewPopup.add(createAlignOrDistributeMenu(home, preferences, true));
      addActionToPopupMenu(ActionType.RESET_FURNITURE_ELEVATION, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_COMPASS, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_WALL, planViewPopup);
      addActionToPopupMenu(ActionType.JOIN_WALLS, planViewPopup);
      addActionToPopupMenu(ActionType.REVERSE_WALL_DIRECTION, planViewPopup);
      addActionToPopupMenu(ActionType.SPLIT_WALL, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_ROOM, planViewPopup);
      JMenuItem addRoomPointMenuItem = addActionToPopupMenu(ActionType.ADD_ROOM_POINT, planViewPopup);
      JMenuItem deleteRoomPointMenuItem = addActionToPopupMenu(ActionType.DELETE_ROOM_POINT, planViewPopup);
      JMenuItem recomputeRoomPointsMenuItem = addActionToPopupMenu(ActionType.RECOMPUTE_ROOM_POINTS, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_POLYLINE, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_DIMENSION_LINE, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_LABEL, planViewPopup);
      planViewPopup.add(createTextStyleMenu(home, preferences, true));
      planViewPopup.addSeparator();
      JMenuItem importModifyBackgroundImageMenuItem = createImportModifyBackgroundImageMenuItem(home, true);
      if (importModifyBackgroundImageMenuItem != null) {
        planViewPopup.add(importModifyBackgroundImageMenuItem);
      }
      JMenuItem hideShowBackgroundImageMenuItem = createHideShowBackgroundImageMenuItem(home, true);
      if (hideShowBackgroundImageMenuItem != null) {
        planViewPopup.add(hideShowBackgroundImageMenuItem);
      }
      addActionToPopupMenu(ActionType.DELETE_BACKGROUND_IMAGE, planViewPopup);
      planViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.ADD_LEVEL, planViewPopup);
      addActionToPopupMenu(ActionType.ADD_LEVEL_AT_SAME_ELEVATION, planViewPopup);
      JMenuItem makeLevelUnviewableViewableMenuItem = createMakeLevelUnviewableViewableMenuItem(home, true);
      if (makeLevelUnviewableViewableMenuItem != null) {
        planViewPopup.add(makeLevelUnviewableViewableMenuItem);
      }
      addActionToPopupMenu(ActionType.MAKE_LEVEL_ONLY_VIEWABLE_ONE, planViewPopup);
      addActionToPopupMenu(ActionType.MAKE_ALL_LEVELS_VIEWABLE, planViewPopup);
      addActionToPopupMenu(ActionType.MODIFY_LEVEL, planViewPopup);
      addActionToPopupMenu(ActionType.DELETE_LEVEL, planViewPopup);
      planViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.ZOOM_OUT, planViewPopup);
      addActionToPopupMenu(ActionType.ZOOM_IN, planViewPopup);
      planViewPopup.addSeparator();
      addActionToPopupMenu(ActionType.EXPORT_TO_SVG, planViewPopup);
      SwingTools.hideDisabledMenuItems(planViewPopup);
      if (selectObjectMenu != null) {
        // Add a popup listener to manage Select object sub menu before the menu is hidden when empty
        addSelectObjectMenuItems(selectObjectMenu, controller.getPlanController(), preferences);
      }
      if (addRoomPointMenuItem != null || deleteRoomPointMenuItem != null || recomputeRoomPointsMenuItem != null) {
        // Add a popup listener to manage ADD_ROOM_POINT, DELETE_ROOM_POINT and RECOMPUTE_ROOM_POINTS actions according to selection
        updateRoomActions(addRoomPointMenuItem, deleteRoomPointMenuItem, recomputeRoomPointsMenuItem,
            controller.getPlanController(), preferences);
      }
      planView.setComponentPopupMenu(planViewPopup);

      final JScrollPane planScrollPane;
      if (planView instanceof Scrollable) {
        planView = planScrollPane
                 = SwingTools.createScrollPane(planView);
      } else {
        List<JScrollPane> scrollPanes = SwingTools.findChildren(planView, JScrollPane.class);
        if (scrollPanes.size() == 1) {
          planScrollPane = scrollPanes.get(0);
        } else {
          planScrollPane = null;
        }
      }

      if (planScrollPane != null) {
        setPlanRulersVisible(planScrollPane, controller, preferences.isRulersVisible());
        if (planScrollPane.getCorner(JScrollPane.UPPER_LEADING_CORNER) == null) {
          final JComponent lockUnlockBasePlanButton = createLockUnlockBasePlanButton(home);
          if (lockUnlockBasePlanButton != null) {
            planScrollPane.setCorner(JScrollPane.UPPER_LEADING_CORNER, lockUnlockBasePlanButton);
            planScrollPane.addPropertyChangeListener("componentOrientation", new PropertyChangeListener() {
              public void propertyChange(PropertyChangeEvent ev) {
                if (lockUnlockBasePlanButton.getParent() != null) {
                  planScrollPane.setCorner(JScrollPane.UPPER_LEADING_CORNER, lockUnlockBasePlanButton);
                }
              }
            });
          }
        }
        // Add a listener to update rulers visibility in preferences
        preferences.addPropertyChangeListener(UserPreferences.Property.RULERS_VISIBLE,
            new RulersVisibilityChangeListener(this, planScrollPane, controller));
        // Restore viewport position if it exists
        final JViewport viewport = planScrollPane.getViewport();
        Number viewportX = home.getNumericProperty(PLAN_VIEWPORT_X_VISUAL_PROPERTY);
        Number viewportY = home.getNumericProperty(PLAN_VIEWPORT_Y_VISUAL_PROPERTY);
        if (viewportX != null && viewportY != null) {
          viewport.setViewPosition(new Point(viewportX.intValue(), viewportY.intValue()));
        }
        viewport.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ev) {
              Point viewportPosition = viewport.getViewPosition();
              controller.setHomeProperty(PLAN_VIEWPORT_X_VISUAL_PROPERTY, String.valueOf(viewportPosition.x));
              controller.setHomeProperty(PLAN_VIEWPORT_Y_VISUAL_PROPERTY, String.valueOf(viewportPosition.y));
            }
          });
      }
    }

    // Configure 3D view
    JComponent view3D = (JComponent)controller.getHomeController3D().getView();
    if (view3D != null) {
      view3D.setPreferredSize(planView != null
          ? planView.getPreferredSize()
          : new Dimension(400, 400));
      view3D.setMinimumSize(new Dimension());

      // Create 3D view popup menu
      JPopupMenu view3DPopup = new JPopupMenu();
      final JMenuItem selectObjectMenuItem = addActionToPopupMenu(ActionType.SELECT_OBJECT, view3DPopup);
      if (selectObjectMenuItem != null) {
        Action toggleSelectionAction = getActionMap().get(ActionType.TOGGLE_SELECTION);
        if (toggleSelectionAction.getValue(Action.NAME) != null) {
          // Change "Select object" menu to "Toggle selection" when shift key is pressed
          final KeyEventDispatcher shiftKeyListener = new KeyEventDispatcher() {
              public boolean dispatchKeyEvent(KeyEvent ev) {
                selectObjectMenuItem.setAction(getActionMap().get(ev.isShiftDown()
                    ? ActionType.TOGGLE_SELECTION
                    : ActionType.SELECT_OBJECT));
                return false;
              }
            };
          addAncestorListener(new AncestorListener() {
              public void ancestorAdded(AncestorEvent event) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(shiftKeyListener);
              }
              public void ancestorRemoved(AncestorEvent event) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(shiftKeyListener);
              }

              public void ancestorMoved(AncestorEvent event) {
              }
            });
        }
      }
      view3DPopup.addSeparator();
      addToggleActionToPopupMenu(ActionType.VIEW_FROM_TOP, true, view3DPopup);
      addToggleActionToPopupMenu(ActionType.VIEW_FROM_OBSERVER, true, view3DPopup);
      addActionToPopupMenu(ActionType.MODIFY_OBSERVER, view3DPopup);
      addActionToPopupMenu(ActionType.STORE_POINT_OF_VIEW, view3DPopup);
      JMenu goToPointOfViewMenu = createGoToPointOfViewMenu(home, preferences, controller);
      if (goToPointOfViewMenu != null) {
        view3DPopup.add(goToPointOfViewMenu);
      }
      addActionToPopupMenu(ActionType.DELETE_POINTS_OF_VIEW, view3DPopup);
      view3DPopup.addSeparator();
      JMenuItem attachDetach3DViewMenuItem = createAttachDetach3DViewMenuItem(controller, true);
      if (attachDetach3DViewMenuItem != null) {
        view3DPopup.add(attachDetach3DViewMenuItem);
      }
      addToggleActionToPopupMenu(ActionType.DISPLAY_ALL_LEVELS, true, view3DPopup);
      addToggleActionToPopupMenu(ActionType.DISPLAY_SELECTED_LEVEL, true, view3DPopup);
      addActionToPopupMenu(ActionType.MODIFY_3D_ATTRIBUTES, view3DPopup);
      view3DPopup.addSeparator();
      addActionToPopupMenu(ActionType.CREATE_PHOTO, view3DPopup);
      addActionToPopupMenu(ActionType.CREATE_PHOTOS_AT_POINTS_OF_VIEW, view3DPopup);
      addActionToPopupMenu(ActionType.CREATE_VIDEO, view3DPopup);
      view3DPopup.addSeparator();
      addActionToPopupMenu(ActionType.EXPORT_TO_OBJ, view3DPopup);
      SwingTools.hideDisabledMenuItems(view3DPopup);
      if (selectObjectMenuItem != null) {
        // Add a popup listener to manage SELECT_OBJECT and TOGGLE_SELECTION actions according to picked point
        updatePickingActions(selectObjectMenuItem, controller.getHomeController3D(), controller.getPlanController(), preferences);
      }
      view3D.setComponentPopupMenu(view3DPopup);

      if (view3D instanceof Scrollable) {
        view3D = SwingTools.createScrollPane(view3D);
      }

      final JComponent planView3DPane;
      boolean detachedView3D = Boolean.parseBoolean(home.getProperty(view3D.getClass().getName() + DETACHED_VIEW_VISUAL_PROPERTY));
      if (planView != null) {
        // Create a split pane that displays both components
        final JSplitPane planView3DSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, planView, view3D);
        planView3DSplitPane.setMinimumSize(new Dimension());
        configureSplitPane((JSplitPane)planView3DSplitPane, home,
            PLAN_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY, 0.5, false, controller);

        final Number dividerLocation = home.getNumericProperty(PLAN_PANE_DIVIDER_LOCATION_VISUAL_PROPERTY);
        if (OperatingSystem.isMacOSX()
            && !OperatingSystem.isJavaVersionGreaterOrEqual("1.7")
            && !detachedView3D && dividerLocation != null && dividerLocation.intValue() > 2
            && !Boolean.getBoolean("com.eteks.sweethome3d.j3d.useOffScreen3DView")) {
          // Under Mac OS X, ensure that the 3D view of an existing home will be displayed during a while
          // to avoid a freeze when the 3D view was saved as hidden and then the window displaying the 3D view is enlarged
          // (this issue happens with on screen canvas under Java3D 1.5.2 and 1.6)
          planView3DSplitPane.addAncestorListener(new AncestorListener() {
              public void ancestorAdded(AncestorEvent event) {
                planView3DSplitPane.removeAncestorListener(this);
                if (planView3DSplitPane.getRightComponent().getHeight() == 0) {
                  // If the 3D view is invisible, make it appear during a while
                  planView3DSplitPane.setDividerLocation(dividerLocation.intValue() - 2);
                  new Timer(1000, new ActionListener() {
                      public void actionPerformed(ActionEvent ev) {
                        ((Timer)ev.getSource()).stop();
                        planView3DSplitPane.setDividerLocation(dividerLocation.intValue());
                      }
                    }).start();
                }
              }

              public void ancestorRemoved(AncestorEvent event) {
              }

              public void ancestorMoved(AncestorEvent event) {
              }
            });
        }

        planView3DPane = planView3DSplitPane;
      } else {
        planView3DPane = view3D;
      }

      // Detach 3D view if it was detached when saved and its dialog can be viewed in one of the screen devices
      if (detachedView3D) {
        final Number dialogX = this.home.getNumericProperty(view3D.getClass().getName() + DETACHED_VIEW_X_VISUAL_PROPERTY);
        final Number dialogY = this.home.getNumericProperty(view3D.getClass().getName() + DETACHED_VIEW_Y_VISUAL_PROPERTY);
        final Number dialogWidth = home.getNumericProperty(view3D.getClass().getName() + DETACHED_VIEW_WIDTH_VISUAL_PROPERTY);
        final Number dialogHeight = home.getNumericProperty(view3D.getClass().getName() + DETACHED_VIEW_HEIGHT_VISUAL_PROPERTY);
        if (dialogX != null && dialogY != null && dialogWidth != null && dialogHeight != null) {
          EventQueue.invokeLater(new Runnable() {
              public void run() {
                View view3D = controller.getHomeController3D().getView();
                // Check 3D view can be viewed in one of the available screens
                if (getActionMap().get(ActionType.DETACH_3D_VIEW).isEnabled()
                    && isRectanglePartiallyVisible(dialogX.intValue(), dialogY.intValue(), dialogWidth.intValue(), dialogHeight.intValue())) {
                  detachView(view3D, dialogX.intValue(), dialogY.intValue(), dialogWidth.intValue(), dialogHeight.intValue());
                } else if (planView3DPane instanceof JSplitPane) {
                  // Restore the divider location of the split pane displaying the 3D view
                  final JSplitPane splitPane = ((JSplitPane)planView3DPane);
                  final Number dividerLocation = home.getNumericProperty(
                      view3D.getClass().getName() + DETACHED_VIEW_DIVIDER_LOCATION_VISUAL_PROPERTY);
                  if (dividerLocation != null
                      && dividerLocation.floatValue() != -1f) {
                    splitPane.setDividerLocation(dividerLocation.floatValue());
                  }
                  controller.setHomeProperty(view3D.getClass().getName() + DETACHED_VIEW_VISUAL_PROPERTY, String.valueOf(false));
                }
              }
            });
          return planView3DPane;
        }
        controller.setHomeProperty(view3D.getClass().getName() + DETACHED_VIEW_X_VISUAL_PROPERTY, null);
      }

      return planView3DPane;
    } else {
      return planView;
    }
  }

  /**
   * Adds to the menu a listener that updates the actions that allow to
   * add or delete points in the selected room.
   */
  private void updateRoomActions(final JMenuItem       addRoomPointMenuItem,
                                 final JMenuItem       deleteRoomPointMenuItem,
                                 final JMenuItem       recomputeRoomPointsMenuItem,
                                 final PlanController  planController,
                                 final UserPreferences preferences) {
    JPopupMenu popupMenu = (JPopupMenu)(addRoomPointMenuItem != null
        ? addRoomPointMenuItem
        : deleteRoomPointMenuItem).getParent();
    popupMenu.addPopupMenuListener(new PopupMenuListenerWithMouseLocation((JComponent)planController.getView()) {
        {
          // Replace ADD_ROOM_POINT, DELETE_ROOM_POINT and RECOMPUTE_ROOM_POINTS actions by ones
          // that will use the mouse location when the popup is displayed
          ActionMap actionMap = getActionMap();
          if (addRoomPointMenuItem != null) {
            ResourceAction addRoomPointAction =
                new ResourceAction(preferences, HomePane.class, ActionType.ADD_ROOM_POINT.name()) {
                  @Override
                  public void actionPerformed(ActionEvent ev) {
                    PlanView planView = planController.getView();
                    planController.addPointToSelectedRoom(
                        planView.convertXPixelToModel(getMouseLocation().x),
                        planView.convertYPixelToModel(getMouseLocation().y));
                  }
                };
            actionMap.put(ActionType.ADD_ROOM_POINT, addRoomPointAction);
            addRoomPointMenuItem.setAction(new ResourceAction.PopupMenuItemAction(addRoomPointAction));
          }
          if (deleteRoomPointMenuItem != null) {
            ResourceAction deleteRoomPointAction =
                new ResourceAction(preferences, HomePane.class, ActionType.DELETE_ROOM_POINT.name()) {
                  @Override
                  public void actionPerformed(ActionEvent ev) {
                    PlanView planView = planController.getView();
                    planController.deletePointFromSelectedRoom(
                        planView.convertXPixelToModel(getMouseLocation().x),
                        planView.convertYPixelToModel(getMouseLocation().y));
                  }
                };
            actionMap.put(ActionType.DELETE_ROOM_POINT, deleteRoomPointAction);
            deleteRoomPointMenuItem.setAction(new ResourceAction.PopupMenuItemAction(deleteRoomPointAction));
          }
          if (recomputeRoomPointsMenuItem != null) {
            ResourceAction recomputeRoomPointsAction =
                new ResourceAction(preferences, HomePane.class, ActionType.RECOMPUTE_ROOM_POINTS.name()) {
                  @Override
                  public void actionPerformed(ActionEvent ev) {
                    PlanView planView = planController.getView();
                    planController.recomputeSelectedRoomPoints(
                        planView.convertXPixelToModel(getMouseLocation().x),
                        planView.convertYPixelToModel(getMouseLocation().y));
                  }
                };
            actionMap.put(ActionType.RECOMPUTE_ROOM_POINTS, recomputeRoomPointsAction);
            recomputeRoomPointsMenuItem.setAction(new ResourceAction.PopupMenuItemAction(recomputeRoomPointsAction));
          }
        }

        private boolean deleteRoomPointActionEnabled;
        private boolean recomputeRoomPointsActionEnabled;

        public void popupMenuWillBecomeVisible(PopupMenuEvent ev) {
          super.popupMenuWillBecomeVisible(ev);
          Point mouseLocation = getMouseLocation();
          if (mouseLocation != null
              && deleteRoomPointMenuItem != null) {
            Action deleteRoomPointAction = getActionMap().get(ActionType.DELETE_ROOM_POINT);
            this.deleteRoomPointActionEnabled = deleteRoomPointAction.isEnabled();
            if (this.deleteRoomPointActionEnabled) {
              // Check mouse location is over a point of the room
              Room selectedRoom = (Room)home.getSelectedItems().get(0);
              float x = planController.getView().convertXPixelToModel(mouseLocation.x);
              float y = planController.getView().convertYPixelToModel(mouseLocation.y);
              deleteRoomPointAction.setEnabled(planController.isRoomPointDeletableAt(selectedRoom, x, y));
            }
            Action recomputeRoomPointsAction = getActionMap().get(ActionType.RECOMPUTE_ROOM_POINTS);
            this.recomputeRoomPointsActionEnabled = recomputeRoomPointsAction.isEnabled();
            if (this.recomputeRoomPointsActionEnabled) {
              // Check room can recomputed at mouse location
              Room selectedRoom = (Room)home.getSelectedItems().get(0);
              float x = planController.getView().convertXPixelToModel(mouseLocation.x);
              float y = planController.getView().convertYPixelToModel(mouseLocation.y);
              recomputeRoomPointsAction.setEnabled(planController.isRoomPointsComputableAt(selectedRoom, x, y));
            }
          }
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent ev) {
          EventQueue.invokeLater(new Runnable() {
              public void run() {
                // Reset action to previous state
                getActionMap().get(ActionType.DELETE_ROOM_POINT).setEnabled(deleteRoomPointActionEnabled);
                getActionMap().get(ActionType.RECOMPUTE_ROOM_POINTS).setEnabled(recomputeRoomPointsActionEnabled);
              }
            });
        }

        public void popupMenuCanceled(PopupMenuEvent ev) {
          popupMenuWillBecomeInvisible(ev);
        }
      });
  }

  /**
   * Adds to the menu a popup listener that will update the menu items able to select
   * the selectable items in plan at the location where the menu will be triggered.
   */
  private void addSelectObjectMenuItems(final JMenu           selectObjectMenu,
                                        final PlanController  planController,
                                        final UserPreferences preferences) {
    ((JPopupMenu)selectObjectMenu.getParent()).addPopupMenuListener(
        new PopupMenuListenerWithMouseLocation((JComponent)planController.getView()) {
          @SuppressWarnings({"rawtypes", "unchecked"})
          public void popupMenuWillBecomeVisible(PopupMenuEvent ev) {
            super.popupMenuWillBecomeVisible(ev);
            Point mouseLocation = getMouseLocation();
            if (mouseLocation != null
                && !planController.isModificationState()) {
              final List<Selectable> items = planController.getSelectableItemsAt(
                  planController.getView().convertXPixelToModel(mouseLocation.x),
                  planController.getView().convertYPixelToModel(mouseLocation.y));
              // Prepare localized formatters
              Map<Class<? extends Selectable>, SelectableFormat> formatters =
                  new HashMap<Class<? extends Selectable>, SelectableFormat>();
              formatters.put(Compass.class, new SelectableFormat<Compass>() {
                  public String format(Compass compass) {
                    return preferences.getLocalizedString(HomePane.class, "selectObject.compass");
                  }
                });
              formatters.put(HomePieceOfFurniture.class, new SelectableFormat<HomePieceOfFurniture>() {
                  public String format(HomePieceOfFurniture piece) {
                    if (piece.getName().length() > 0) {
                      return piece.getName();
                    } else {
                      return preferences.getLocalizedString(HomePane.class, "selectObject.furniture");
                    }
                  }
                });
              formatters.put(Wall.class, new SelectableFormat<Wall>() {
                  public String format(Wall wall) {
                    return preferences.getLocalizedString(HomePane.class, "selectObject.wall",
                        preferences.getLengthUnit().getFormatWithUnit().format(wall.getLength()));
                  }
                });
              formatters.put(Room.class, new SelectableFormat<Room>() {
                  public String format(Room room) {
                    String roomInfo = room.getName() != null && room.getName().length() > 0
                        ? room.getName()
                        : (room.isAreaVisible()
                              ? preferences.getLengthUnit().getAreaFormatWithUnit().format(room.getArea())
                              : "");
                    if (room.isFloorVisible() && !room.isCeilingVisible()) {
                      return preferences.getLocalizedString(HomePane.class, "selectObject.floor", roomInfo);
                    } else if (!room.isFloorVisible() && room.isCeilingVisible()) {
                      return preferences.getLocalizedString(HomePane.class, "selectObject.ceiling", roomInfo);
                    } else {
                      return preferences.getLocalizedString(HomePane.class, "selectObject.room", roomInfo);
                    }
                  }
                });
              formatters.put(Polyline.class, new SelectableFormat<Polyline>() {
                  public String format(Polyline polyline) {
                    return preferences.getLocalizedString(HomePane.class, "selectObject.polyline",
                        preferences.getLengthUnit().getFormatWithUnit().format(polyline.getLength()));
                  }
                });
              formatters.put(DimensionLine.class, new SelectableFormat<DimensionLine>() {
                  public String format(DimensionLine dimensionLine) {
                    return preferences.getLocalizedString(HomePane.class, "selectObject.dimensionLine",
                        preferences.getLengthUnit().getFormatWithUnit().format(dimensionLine.getLength()));
                  }
                });
              formatters.put(Label.class, new SelectableFormat<Label>() {
                  public String format(Label label) {
                    if (label.getText().length() > 0) {
                      return label.getText();
                    } else {
                      return preferences.getLocalizedString(HomePane.class, "selectObject.label");
                    }
                  }
                });

              for (final Selectable item : items) {
                String format = null;
                for (Map.Entry<Class<? extends Selectable>, SelectableFormat> entry : formatters.entrySet()) {
                  if (entry.getKey().isInstance(item)) {
                    format = entry.getValue().format(item);
                    break;
                  }
                }
                if (format != null) {
                  selectObjectMenu.add(new JMenuItem(new AbstractAction(format) {
                      public void actionPerformed(ActionEvent ev) {
                        if ((ev.getModifiers() & ActionEvent.SHIFT_MASK) == ActionEvent.SHIFT_MASK) {
                          planController.toggleItemSelection(item);
                        } else {
                          planController.selectItem(item);
                        }
                      }
                    }));
                }
              }
            }
          }

          public void popupMenuWillBecomeInvisible(PopupMenuEvent ev) {
            selectObjectMenu.removeAll();
          }

          public void popupMenuCanceled(PopupMenuEvent ev) {
          }
        });
  }

  /**
   * Adds to the menu a listener that updates the actions that allow to
   * pick and select an object.
   */
  private void updatePickingActions(final JMenuItem        selectObjectMenuItem,
                                    final HomeController3D homeController3D,
                                    final PlanController   planController,
                                    final UserPreferences  preferences) {
    JPopupMenu popupMenu = (JPopupMenu)selectObjectMenuItem.getParent();
    popupMenu.addPopupMenuListener(new PopupMenuListenerWithMouseLocation((JComponent)homeController3D.getView()) {
        {
          selectObjectMenuItem.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ev) {
                if ((ev.getModifiers() & ActionEvent.SHIFT_MASK) == ActionEvent.SHIFT_MASK) {
                  planController.toggleItemSelection(selectableItem);
                } else {
                  planController.selectItem(selectableItem);
                  if (selectableItem instanceof Elevatable
                      && !((Elevatable)selectableItem).isAtLevel(home.getSelectedLevel())) {
                    planController.setSelectedLevel(((Elevatable)selectableItem).getLevel());
                  }
                  planController.getView().makeSelectionVisible();
                }
              }
            });
        }

        private Selectable selectableItem;

        public void popupMenuWillBecomeVisible(PopupMenuEvent ev) {
          super.popupMenuWillBecomeVisible(ev);
          Point mouseLocation = getMouseLocation();
          if (mouseLocation != null
              && planController != null
              && !planController.isModificationState()) {
            this.selectableItem = homeController3D.getView() instanceof View3D
                ? ((View3D)homeController3D.getView()).getClosestSelectableItemAt(mouseLocation.x, mouseLocation.y)
                : null;
          } else {
            this.selectableItem = null;
          }
          getActionMap().get(ActionType.SELECT_OBJECT).setEnabled(this.selectableItem != null);
          getActionMap().get(ActionType.TOGGLE_SELECTION).setEnabled(this.selectableItem != null);
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent ev) {
        }

        public void popupMenuCanceled(PopupMenuEvent ev) {
        }
      });
  }

  /**
   * Preferences property listener bound to this component with a weak reference to avoid
   * strong link between preferences and this component.
   */
  private static class RulersVisibilityChangeListener implements PropertyChangeListener {
    private WeakReference<HomePane>       homePane;
    private WeakReference<JScrollPane>    planScrollPane;
    private WeakReference<HomeController> controller;

    public RulersVisibilityChangeListener(HomePane homePane,
                                          JScrollPane planScrollPane,
                                          HomeController controller) {
      this.homePane = new WeakReference<HomePane>(homePane);
      this.planScrollPane = new WeakReference<JScrollPane>(planScrollPane);
      this.controller = new WeakReference<HomeController>(controller);
    }

    public void propertyChange(PropertyChangeEvent ev) {
      // If home pane was garbage collected, remove this listener from preferences
      HomePane homePane = this.homePane.get();
      JScrollPane planScrollPane = this.planScrollPane.get();
      HomeController controller = this.controller.get();
      if (homePane == null
          || planScrollPane == null
          || controller == null) {
        ((UserPreferences)ev.getSource()).removePropertyChangeListener(
            UserPreferences.Property.RULERS_VISIBLE, this);
      } else {
        homePane.setPlanRulersVisible(planScrollPane, controller, (Boolean)ev.getNewValue());
      }
    }
  }

  /**
   * Sets the rulers visible in plan view.
   */
  private void setPlanRulersVisible(JScrollPane planScrollPane,
                                    HomeController controller, boolean visible) {
    if (visible) {
      // Change column and row header views
      planScrollPane.setColumnHeaderView(
          (JComponent)controller.getPlanController().getHorizontalRulerView());
      planScrollPane.setRowHeaderView(
          (JComponent)controller.getPlanController().getVerticalRulerView());
    } else {
      planScrollPane.setColumnHeaderView(null);
      planScrollPane.setRowHeaderView(null);
    }
  }

  /**
   * Adds to <code>view</code> a mouse listener that disables all menu items of
   * <code>menuBar</code> during a drag and drop operation in <code>view</code>.
   */
  private void disableMenuItemsDuringDragAndDrop(View view,
                                                 final JMenuBar menuBar) {
    class MouseAndFocusListener extends MouseAdapter implements FocusListener {
      @Override
      public void mousePressed(MouseEvent ev) {
        if (SwingUtilities.isLeftMouseButton(ev)) {
          setMenusEnabled(menuBar, false);
        }
      }

      @Override
      public void mouseReleased(MouseEvent ev) {
        if (SwingUtilities.isLeftMouseButton(ev)) {
          setMenusEnabled(menuBar, true);
        }
      }

      // Need to take into account focus events because a mouse released event
      // isn't dispatched when the component loses focus
      public void focusGained(FocusEvent ev) {
        setMenusEnabled(menuBar, true);
      }

      public void focusLost(FocusEvent ev) {
        setMenusEnabled(menuBar, true);
      }

      /**
       * Enables or disables the menu items of the given menu bar.
       */
      private void setMenusEnabled(final JMenuBar menuBar, final boolean enabled) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
              for (int i = 0, n = menuBar.getMenuCount(); i < n; i++) {
                setMenuItemsEnabled(menuBar.getMenu(i), enabled);
              }
            }
          });
      }

      /**
       * Enables or disables the menu items of the given <code>menu</code>.
       */
      private void setMenuItemsEnabled(JMenu menu, boolean enabled) {
        for (int i = 0, n = menu.getItemCount(); i < n; i++) {
          JMenuItem item = menu.getItem(i);
          if (item instanceof JMenu) {
            setMenuItemsEnabled((JMenu)item, enabled);
          } else if (item != null) {
            item.setEnabled(enabled
                ? item.getAction().isEnabled()
                : false);
          }
        }
      }
    };

    MouseAndFocusListener listener = new MouseAndFocusListener();
    if (view != null) {
      ((JComponent)view).addMouseListener(listener);
      ((JComponent)view).addFocusListener(listener);
    }
  }

  /**
   * Detaches the given <code>view</code> from home view.
   */
  public void detachView(final View view) {
    JComponent component = (JComponent)view;
    Container parent = component.getParent();
    if (parent instanceof JViewport) {
      component = (JComponent)parent.getParent();
      parent = component.getParent();
    }

    float dividerLocation;
    if (parent instanceof JSplitPane) {
      JSplitPane splitPane = (JSplitPane)parent;
      if (splitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
        dividerLocation = (float)splitPane.getDividerLocation()
            / (splitPane.getHeight() - splitPane.getDividerSize());
      } else {
        dividerLocation = (float)splitPane.getDividerLocation()
          / (splitPane.getWidth() - splitPane.getDividerSize());
      }
    } else {
      dividerLocation = -1;
    }

    Number dialogX = this.home.getNumericProperty(view.getClass().getName() + DETACHED_VIEW_X_VISUAL_PROPERTY);
    Number dialogY = this.home.getNumericProperty(view.getClass().getName() + DETACHED_VIEW_Y_VISUAL_PROPERTY);
    Number dialogWidth = this.home.getNumericProperty(view.getClass().getName() + DETACHED_VIEW_WIDTH_VISUAL_PROPERTY);
    Number dialogHeight = this.home.getNumericProperty(view.getClass().getName() + DETACHED_VIEW_HEIGHT_VISUAL_PROPERTY);
    if (dialogX != null && dialogY != null && dialogWidth != null && dialogHeight != null) {
      detachView(view, dialogX.intValue(), dialogY.intValue(), dialogWidth.intValue(), dialogHeight.intValue());
    } else {
      Point componentLocation = new Point();
      Dimension componentSize = component.getSize();
      Dimension preferredSize = component.getPreferredSize();
      SwingUtilities.convertPointToScreen(componentLocation, component);

      Insets insets = new JDialog().getInsets();
      detachView(view, componentLocation.x - insets.left,
          componentLocation.y - insets.top,
          (componentSize.width == 0 || componentSize.height == 0 ? Math.max(preferredSize.width, componentSize.width) : componentSize.width) + insets.left + insets.right,
          (componentSize.width == 0 || componentSize.height == 0 ? Math.max(preferredSize.height, componentSize.height) : componentSize.height) + insets.top + insets.bottom);
    }
    this.controller.setHomeProperty(view.getClass().getName() + DETACHED_VIEW_DIVIDER_LOCATION_VISUAL_PROPERTY, String.valueOf(dividerLocation));
  }

  /**
   * Detaches a <code>view</code> at the given location and size.
   */
  private void detachView(final View view, int x, int y, int width, int height) {
    JComponent component = (JComponent)view;
    Container parent = component.getParent();
    if (parent instanceof JViewport) {
      component = (JComponent)parent.getParent();
      parent = component.getParent();
    }

    // Replace component by a dummy panel to find easily where to attach back the component
    final JPanel dummyPanel = new JPanel();
    dummyPanel.setMaximumSize(new Dimension());
    dummyPanel.setMinimumSize(new Dimension());
    dummyPanel.setName(view.getClass().getName());
    dummyPanel.setBorder(component.getBorder());

    if (parent instanceof JSplitPane) {
      final JSplitPane splitPane = (JSplitPane)parent;
      splitPane.setDividerSize(0);
      final float dividerLocation;
      if (splitPane.getLeftComponent() == component) {
        splitPane.setLeftComponent(dummyPanel);
        dividerLocation = 0f;
      } else {
        splitPane.setRightComponent(dummyPanel);
        dividerLocation = 1f;
      }
      splitPane.setDividerLocation(dividerLocation);
      dummyPanel.addComponentListener(new ComponentAdapter() {
          @Override
          public void componentResized(ComponentEvent ev) {
            // Force divider location even if maximum size is set to zero
            // to ensure the dummy panel won't appear
            dummyPanel.removeComponentListener(this);
            splitPane.setDividerLocation(dividerLocation);
            dummyPanel.addComponentListener(this);
          }
        });
    } else {
      int componentIndex = parent.getComponentZOrder(component);
      parent.remove(componentIndex);
      parent.add(dummyPanel, componentIndex);
    }

    // Display view in a separate non modal dialog
    Window window = SwingUtilities.getWindowAncestor(this);
    if (!(window instanceof Frame)) {
      window = JOptionPane.getRootFrame();
    }
    Frame defaultFrame = (Frame)window;
    // Create a dialog with the same title as home frame
    final Window separateWindow;
    if (OperatingSystem.isMacOSX()
        && OperatingSystem.isJavaVersionGreaterOrEqual("1.7")
        && GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length > 1) {
      // Under Mac OS X, handle separate window with a JFrame in multiple screens environment
      // because a separate JDialog instance displaying a 3D view jumps back to the screen
      // where the main window is displayed when a modification dialog is opened
      final JFrame separateFrame = new JFrame(defaultFrame.getTitle(), defaultFrame.getGraphicsConfiguration());
      separateFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      if (defaultFrame instanceof JFrame) {
        separateFrame.setJMenuBar(createMenuBar(this.home, this.preferences, this.controller));
      }
      try {
        // Call Java 1.6 setIconImages by reflection to ensure the iconnable frame will have a good icon
        JFrame.class.getMethod("setIconImages", List.class).invoke(separateFrame,
            JFrame.class.getMethod("getIconImages").invoke(defaultFrame));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      // Close separate frame when main frame is closed
      defaultFrame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosed(WindowEvent ev) {
            separateFrame.dispose();
            ev.getWindow().removeWindowListener(this);
          }
        });
      separateWindow = separateFrame;
    } else {
      JDialog separateDialog = new JDialog(defaultFrame, defaultFrame.getTitle(), false);
      separateDialog.setResizable(true);
      separateDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      // Copy action map and input map to enable shortcuts in the window
      ActionMap actionMap = getActionMap();
      separateDialog.getRootPane().setActionMap(actionMap);
      InputMap inputMap = separateDialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      for (Object key : actionMap.allKeys()) {
        Action action = actionMap.get(key);
        KeyStroke accelerator = (KeyStroke)action.getValue(Action.ACCELERATOR_KEY);
        if (key != ActionType.CLOSE
            && key != ActionType.DETACH_3D_VIEW
            && (key != ActionType.EXIT || !OperatingSystem.isMacOSX())
            && accelerator != null) {
          inputMap.put(accelerator, key);
        }
      }
      separateWindow = separateDialog;
    }

    defaultFrame.addPropertyChangeListener("title", new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent ev) {
          if (separateWindow instanceof JFrame) {
            ((JFrame)separateWindow).setTitle((String)ev.getNewValue());
          } else {
            ((JDialog)separateWindow).setTitle((String)ev.getNewValue());
          }
        }
      });
    final JRootPane separateRootPane = ((RootPaneContainer)separateWindow).getRootPane();
    if (defaultFrame instanceof RootPaneContainer) {
      // Use same document modified indicator
      if (OperatingSystem.isMacOSXLeopardOrSuperior()) {
        ((RootPaneContainer)defaultFrame).getRootPane().addPropertyChangeListener("Window.documentModified", new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            separateRootPane.putClientProperty("Window.documentModified", ev.getNewValue());
          }
        });
      } else if (OperatingSystem.isMacOSX()) {
        ((RootPaneContainer)defaultFrame).getRootPane().addPropertyChangeListener("windowModified", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent ev) {
              separateRootPane.putClientProperty("windowModified", ev.getNewValue());
            }
          });
      }
    }

    separateRootPane.setContentPane(component);
    separateWindow.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent ev) {
          controller.attachView(view);
        }
      });
    separateWindow.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent ev) {
          controller.setHomeProperty(view.getClass().getName() + DETACHED_VIEW_WIDTH_VISUAL_PROPERTY, String.valueOf(separateWindow.getWidth()));
          controller.setHomeProperty(view.getClass().getName() + DETACHED_VIEW_HEIGHT_VISUAL_PROPERTY, String.valueOf(separateWindow.getHeight()));
        }

        @Override
        public void componentMoved(ComponentEvent ev) {
          controller.setHomeProperty(view.getClass().getName() + DETACHED_VIEW_X_VISUAL_PROPERTY, String.valueOf(separateWindow.getX()));
          controller.setHomeProperty(view.getClass().getName() + DETACHED_VIEW_Y_VISUAL_PROPERTY, String.valueOf(separateWindow.getY()));
        }
      });

    separateWindow.setBounds(x, y, width, height);
    separateWindow.setLocationByPlatform(!isRectanglePartiallyVisible(x, y, width, height));
    separateWindow.setVisible(true);

    this.controller.setHomeProperty(view.getClass().getName() + DETACHED_VIEW_VISUAL_PROPERTY, Boolean.TRUE.toString());
  }

  /**
   * Returns <code>true</code> if at least 10% of the given rectangle is partially visible at screen.
   */
  private boolean isRectanglePartiallyVisible(int x, int y, int width, int height) {
    Area rectangle = new Area(new Rectangle(x, y, width, height));
    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    for (GraphicsDevice device : environment.getScreenDevices()) {
      Area intersectionArea = new Area(device.getDefaultConfiguration().getBounds());
      intersectionArea.intersect(rectangle);
      Rectangle intersectionBounds = intersectionArea.getBounds();
      if (intersectionBounds.getWidth() * intersectionBounds.getHeight() >= width * height / 10) {
        return true;
      }
    }
    return false;
  }

  /**
   * Attaches the given <code>view</code> to home view.
   */
  public void attachView(final View view) {
    this.controller.setHomeProperty(view.getClass().getName() + DETACHED_VIEW_VISUAL_PROPERTY, String.valueOf(false));

    JComponent dummyComponent = (JComponent)findChild(this, view.getClass().getName());
    if (dummyComponent != null) {
      // First dispose detached view window to avoid possible issues on multiple screens graphics environment
      JComponent component = (JComponent)view;
      Window window = SwingUtilities.getWindowAncestor(component);
      ((RootPaneContainer)window).getRootPane().setActionMap(null);
      window.dispose();
      // Replace dummy component by attached view
      component.setBorder(dummyComponent.getBorder());
      Container parent = dummyComponent.getParent();
      if (parent instanceof JSplitPane) {
        JSplitPane splitPane = (JSplitPane)parent;
        splitPane.setDividerSize(UIManager.getInt("SplitPane.dividerSize"));
        Number dividerLocation = this.home.getNumericProperty(
            view.getClass().getName() + DETACHED_VIEW_DIVIDER_LOCATION_VISUAL_PROPERTY);
        if (dividerLocation != null) {
          splitPane.setDividerLocation(dividerLocation.floatValue());
        }
        if (splitPane.getLeftComponent() == dummyComponent) {
          splitPane.setLeftComponent(component);
        } else {
          splitPane.setRightComponent(component);
        }
      } else {
        int componentIndex = parent.getComponentZOrder(dummyComponent);
        parent.remove(componentIndex);
        parent.add(component, componentIndex);
      }
    }
  }

  /**
   * Returns among <code>parent</code> children the first child with the given name.
   */
  private Component findChild(Container parent, String childName) {
    for (int i = 0; i < parent.getComponentCount(); i++) {
      Component child = parent.getComponent(i);
      if (childName.equals(child.getName())) {
        return child;
      } else if (child instanceof Container) {
        child = findChild((Container)child, childName);
        if (child != null) {
          return child;
        }
      }
    }
    return null;
  }

  /**
   * Displays a content chooser open dialog to choose the name of a home.
   */
  public String showOpenDialog() {
    return this.controller.getContentManager().showOpenDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "openHomeDialog.title"),
        ContentManager.ContentType.SWEET_HOME_3D);
  }

  /**
   * Displays a dialog to let the user choose a home example.
   */
  public String showNewHomeFromExampleDialog() {
    String message = this.preferences.getLocalizedString(HomePane.class, "newHomeFromExample.message");
    String title = this.preferences.getLocalizedString(HomePane.class, "newHomeFromExample.title");
    final String useSelectedHome = this.preferences.getLocalizedString(HomePane.class, "newHomeFromExample.useSelectedExample");
    String findMoreExamples = this.preferences.getLocalizedString(HomePane.class, "newHomeFromExample.findMoreExamples");
    String cancel = this.preferences.getLocalizedString(HomePane.class, "newHomeFromExample.cancel");
    final JList homeExamplesList = new JList(this.preferences.getHomeExamples().toArray()) {
        @Override
        public String getToolTipText(MouseEvent ev) {
          int index = locationToIndex(ev.getPoint());
          // Display full name in tool tip in case label renderer is too short
          return index != -1
              ? ((HomeDescriptor)getModel().getElementAt(index)).getName()
              : null;
        }
      };
    homeExamplesList.setSelectionModel(new DefaultListSelectionModel() {
        @Override
        public void removeSelectionInterval(int index0, int index1) {
          // Do nothing, to avoid empty selection in case of Ctrl or cmd + click
        }
      });
    homeExamplesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    homeExamplesList.setSelectedIndex(0);
    homeExamplesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    homeExamplesList.setVisibleRowCount(3);
    final int iconWidth = (int)(192 * SwingTools.getResolutionScale());
    homeExamplesList.setFixedCellWidth(iconWidth + 8);
    homeExamplesList.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
          HomeDescriptor home = (HomeDescriptor)value;
          super.getListCellRendererComponent(list, home.getName(), index, isSelected, cellHasFocus);
          setIcon(IconManager.getInstance().getIcon(home.getIcon(), iconWidth * 3 / 4, homeExamplesList));
          setHorizontalAlignment(CENTER);
          setHorizontalTextPosition(CENTER);
          setVerticalTextPosition(BOTTOM);
          setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(2, 0, 2, 0)));
          return this;
        }
      });
    homeExamplesList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent ev) {
          if (ev.getClickCount() == 2) {
            ((JOptionPane)SwingUtilities.getAncestorOfClass(JOptionPane.class, ev.getComponent())).setValue(useSelectedHome);
          }
        }
      });

    JPanel panel = new JPanel(new GridBagLayout());
    panel.add(new JLabel(message), new GridBagConstraints(
        0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START,
        GridBagConstraints.NONE, new Insets(0, 0, (int)(5 * SwingTools.getResolutionScale()), 0), 0, 0));
    panel.add(new JScrollPane(homeExamplesList), new GridBagConstraints(
        0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
        GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    Object [] options = findMoreExamples.length() > 0
        ? new Object [] {useSelectedHome, findMoreExamples, cancel}
        : new Object [] {useSelectedHome, cancel};
    int option = JOptionPane.showOptionDialog(this, panel, title,
        findMoreExamples.length() > 0 ? JOptionPane.YES_NO_CANCEL_OPTION : JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, useSelectedHome);
    switch (option) {
      // Convert showOptionDialog answer to SaveAnswer enum constants
      case JOptionPane.YES_OPTION :
        Content homeContent = ((HomeDescriptor)homeExamplesList.getSelectedValue()).getContent();
        return homeContent instanceof URLContent
            ? ((URLContent)homeContent).getURL().toString()
            : null;
      case JOptionPane.NO_OPTION :
        if (findMoreExamples.length() > 0) {
          String findModelsUrl = preferences.getLocalizedString(HomePane.class, "findMoreExamples.url");
          boolean documentShown = false;
          try {
            // Display Find more demos (gallery) page in browser
            documentShown = SwingTools.showDocumentInBrowser(new URL(findModelsUrl));
          } catch (MalformedURLException ex) {
            // Document isn't shown
          }
          if (!documentShown) {
            // If the document wasn't shown, display a message
            // with a copiable URL in a message box
            JTextArea findMoreExamplesMessageTextArea = new JTextArea(preferences.getLocalizedString(
                HomePane.class, "findMoreExamplesMessage.text"));
            String findMoreExamplesTitle = preferences.getLocalizedString(
                HomePane.class, "findMoreExamplesMessage.title");
            findMoreExamplesMessageTextArea.setEditable(false);
            findMoreExamplesMessageTextArea.setOpaque(false);
            SwingTools.showMessageDialog(this,
                findMoreExamplesMessageTextArea, findMoreExamplesTitle, JOptionPane.INFORMATION_MESSAGE);
          }
        }
        // No break
      default :
        return null;
    }
  }

  /**
   * Displays a dialog that lets user choose what he wants to do with a damaged home he tries to open it.
   * @return {@link com.eteks.sweethome3d.viewcontroller.HomeView.OpenDamagedHomeAnswer#REMOVE_DAMAGED_ITEMS}
   * if the user chose to remove damaged items,
   * {@link com.eteks.sweethome3d.viewcontroller.HomeView.OpenDamagedHomeAnswer#REPLACE_DAMAGED_ITEMS}
   * if he doesn't want to replace damaged items by red images and red boxes,
   * or {@link com.eteks.sweethome3d.viewcontroller.HomeView.OpenDamagedHomeAnswer#DO_NOT_OPEN_HOME}
   * if he doesn't want to open damaged home.
   */
  public OpenDamagedHomeAnswer confirmOpenDamagedHome(String homeName,
                                                      Home damagedHome,
                                                      List<Content> invalidContent) {
    // Retrieve displayed text in buttons and message
    String message = this.preferences.getLocalizedString(HomePane.class, "openDamagedHome.message",
        homeName, Math.max(1, invalidContent.size()));
    String title = this.preferences.getLocalizedString(HomePane.class, "openDamagedHome.title");
    String removeDamagedItems = this.preferences.getLocalizedString(HomePane.class, "openDamagedHome.removeDamagedItems");
    String replaceDamagedItems = this.preferences.getLocalizedString(HomePane.class, "openDamagedHome.replaceDamagedItems");
    String doNotOpenHome = this.preferences.getLocalizedString(HomePane.class, "openDamagedHome.doNotOpenHome");

    switch (SwingTools.showOptionDialog(this, message, title,
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
        new Object [] {removeDamagedItems, replaceDamagedItems, doNotOpenHome}, doNotOpenHome)) {
      // Convert showOptionDialog answer to SaveAnswer enum constants
      case JOptionPane.YES_OPTION:
        return OpenDamagedHomeAnswer.REMOVE_DAMAGED_ITEMS;
      case JOptionPane.NO_OPTION:
        return OpenDamagedHomeAnswer.REPLACE_DAMAGED_ITEMS;
      default : return OpenDamagedHomeAnswer.DO_NOT_OPEN_HOME;
    }
  }

  /**
   * Displays a content chooser open dialog to choose a language library.
   */
  public String showImportLanguageLibraryDialog() {
    return this.controller.getContentManager().showOpenDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "importLanguageLibraryDialog.title"),
        ContentManager.ContentType.LANGUAGE_LIBRARY);
  }

  /**
   * Displays a dialog that lets user choose whether he wants to overwrite
   * an existing language library or not.
   */
  public boolean confirmReplaceLanguageLibrary(String languageLibraryName) {
    // Retrieve displayed text in buttons and message
    String message = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceLanguageLibrary.message",
        this.controller.getContentManager().getPresentationName(languageLibraryName, ContentManager.ContentType.LANGUAGE_LIBRARY));
    String title = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceLanguageLibrary.title");
    String replace = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceLanguageLibrary.replace");
    String doNotReplace = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceLanguageLibrary.doNotReplace");

    return SwingTools.showOptionDialog(this,
        message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {replace, doNotReplace}, doNotReplace) == JOptionPane.OK_OPTION;
  }

  /**
   * Displays a content chooser open dialog to choose a furniture library.
   */
  public String showImportFurnitureLibraryDialog() {
    return this.controller.getContentManager().showOpenDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "importFurnitureLibraryDialog.title"),
        ContentManager.ContentType.FURNITURE_LIBRARY);
  }

  /**
   * Displays a dialog that lets user choose whether he wants to overwrite
   * an existing furniture library or not.
   */
  public boolean confirmReplaceFurnitureLibrary(String furnitureLibraryName) {
    // Retrieve displayed text in buttons and message
    String message = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceFurnitureLibrary.message",
        this.controller.getContentManager().getPresentationName(furnitureLibraryName, ContentManager.ContentType.FURNITURE_LIBRARY));
    String title = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceFurnitureLibrary.title");
    String replace = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceFurnitureLibrary.replace");
    String doNotReplace = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceFurnitureLibrary.doNotReplace");

    return SwingTools.showOptionDialog(this,
        message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {replace, doNotReplace}, doNotReplace) == JOptionPane.OK_OPTION;
  }

  /**
   * Displays a content chooser open dialog to choose a textures library.
   */
  public String showImportTexturesLibraryDialog() {
    return this.controller.getContentManager().showOpenDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "importTexturesLibraryDialog.title"),
        ContentManager.ContentType.TEXTURES_LIBRARY);
  }

  /**
   * Displays a dialog that lets user choose whether he wants to overwrite
   * an existing textures library or not.
   */
  public boolean confirmReplaceTexturesLibrary(String texturesLibraryName) {
    // Retrieve displayed text in buttons and message
    String message = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceTexturesLibrary.message",
        this.controller.getContentManager().getPresentationName(texturesLibraryName, ContentManager.ContentType.TEXTURES_LIBRARY));
    String title = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceTexturesLibrary.title");
    String replace = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceTexturesLibrary.replace");
    String doNotReplace = this.preferences.getLocalizedString(HomePane.class, "confirmReplaceTexturesLibrary.doNotReplace");

    return SwingTools.showOptionDialog(this,
        message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {replace, doNotReplace}, doNotReplace) == JOptionPane.OK_OPTION;
  }

  /**
   * Displays a dialog that lets user choose whether he wants to overwrite
   * an existing plug-in or not.
   */
  public boolean confirmReplacePlugin(String pluginName) {
    // Retrieve displayed text in buttons and message
    String message = this.preferences.getLocalizedString(HomePane.class, "confirmReplacePlugin.message",
        this.controller.getContentManager().getPresentationName(pluginName, ContentManager.ContentType.PLUGIN));
    String title = this.preferences.getLocalizedString(HomePane.class, "confirmReplacePlugin.title");
    String replace = this.preferences.getLocalizedString(HomePane.class, "confirmReplacePlugin.replace");
    String doNotReplace = this.preferences.getLocalizedString(HomePane.class, "confirmReplacePlugin.doNotReplace");

    return SwingTools.showOptionDialog(this,
        message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {replace, doNotReplace}, doNotReplace) == JOptionPane.OK_OPTION;
  }

  /**
   * Displays a content chooser save dialog to choose the name of a home.
   */
  public String showSaveDialog(String homeName) {
    return this.controller.getContentManager().showSaveDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "saveHomeDialog.title"),
        ContentManager.ContentType.SWEET_HOME_3D, homeName);
  }

  /**
   * Displays <code>message</code> in an error message box.
   */
  public void showError(String message) {
    String title = this.preferences.getLocalizedString(HomePane.class, "error.title");
    SwingTools.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays <code>message</code> in a message box.
   */
  public void showMessage(String message) {
    String title = this.preferences.getLocalizedString(HomePane.class, "message.title");
    SwingTools.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Displays the tip matching <code>actionTipKey</code> and
   * returns <code>true</code> if the user chose not to display again the tip.
   */
  public boolean showActionTipMessage(String actionTipKey) {
    String title = this.preferences.getLocalizedString(HomePane.class, actionTipKey + ".tipTitle");
    String message = this.preferences.getLocalizedString(HomePane.class, actionTipKey + ".tipMessage");
    if (message.length() > 0) {
      JPanel tipPanel = new JPanel(new GridBagLayout());

      JLabel messageLabel = new JLabel(message);
      tipPanel.add(messageLabel, new GridBagConstraints(
          0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
          GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));

      // Add a check box that lets user choose whether he wants to display again the tip or not
      JCheckBox doNotDisplayTipCheckBox = new JCheckBox(
          SwingTools.getLocalizedLabelText(this.preferences, HomePane.class, "doNotDisplayTipCheckBox.text"));
      if (!OperatingSystem.isMacOSX()) {
        doNotDisplayTipCheckBox.setMnemonic(KeyStroke.getKeyStroke(
            this.preferences.getLocalizedString(HomePane.class, "doNotDisplayTipCheckBox.mnemonic")).getKeyCode());
      }
      tipPanel.add(doNotDisplayTipCheckBox, new GridBagConstraints(
          0, 1, 1, 1, 0, 1, GridBagConstraints.CENTER,
          GridBagConstraints.NONE, new Insets(0, 0, (int)(5 * SwingTools.getResolutionScale()), 0), 0, 0));

      SwingTools.showMessageDialog(this, tipPanel, title,
          JOptionPane.INFORMATION_MESSAGE, doNotDisplayTipCheckBox);
      return doNotDisplayTipCheckBox.isSelected();
    } else {
      // Ignore untranslated tips
      return true;
    }
  }

  /**
   * Displays a dialog that lets user choose whether he wants to save
   * the current home or not.
   * @return {@link com.eteks.sweethome3d.viewcontroller.HomeView.SaveAnswer#SAVE}
   * if the user chose to save home,
   * {@link com.eteks.sweethome3d.viewcontroller.HomeView.SaveAnswer#DO_NOT_SAVE}
   * if he doesn't want to save home,
   * or {@link com.eteks.sweethome3d.viewcontroller.HomeView.SaveAnswer#CANCEL}
   * if he doesn't want to continue current operation.
   */
  public SaveAnswer confirmSave(String homeName) {
    // Retrieve displayed text in buttons and message
    String message;
    if (homeName != null) {
      message = this.preferences.getLocalizedString(HomePane.class, "confirmSave.message",
          "\"" + this.controller.getContentManager().getPresentationName(
              homeName, ContentManager.ContentType.SWEET_HOME_3D) + "\"");
    } else {
      message = this.preferences.getLocalizedString(HomePane.class, "confirmSave.message", "");
    }

    String title = this.preferences.getLocalizedString(HomePane.class, "confirmSave.title");
    String save = this.preferences.getLocalizedString(HomePane.class, "confirmSave.save");
    String doNotSave = this.preferences.getLocalizedString(HomePane.class, "confirmSave.doNotSave");
    String cancel = this.preferences.getLocalizedString(HomePane.class, "confirmSave.cancel");

    switch (SwingTools.showOptionDialog(this, message, title,
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {save, doNotSave, cancel}, save)) {
      // Convert showOptionDialog answer to SaveAnswer enum constants
      case JOptionPane.YES_OPTION:
        return SaveAnswer.SAVE;
      case JOptionPane.NO_OPTION:
        return SaveAnswer.DO_NOT_SAVE;
      default : return SaveAnswer.CANCEL;
    }
  }

  /**
   * Displays a dialog that let user choose whether he wants to save
   * a home that was created with a newer version of Sweet Home 3D.
   * @return <code>true</code> if user confirmed to save.
   */
  public boolean confirmSaveNewerHome(String homeName) {
    String message = this.preferences.getLocalizedString(HomePane.class, "confirmSaveNewerHome.message",
        this.controller.getContentManager().getPresentationName(
            homeName, ContentManager.ContentType.SWEET_HOME_3D));
    String title = this.preferences.getLocalizedString(HomePane.class, "confirmSaveNewerHome.title");
    String save = this.preferences.getLocalizedString(HomePane.class, "confirmSaveNewerHome.save");
    String doNotSave = this.preferences.getLocalizedString(HomePane.class, "confirmSaveNewerHome.doNotSave");

    return SwingTools.showOptionDialog(this, message, title,
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {save, doNotSave}, doNotSave) == JOptionPane.YES_OPTION;
  }

  /**
   * Displays a dialog that let user choose whether he wants to exit
   * application or not.
   * @return <code>true</code> if user confirmed to exit.
   */
  public boolean confirmExit() {
    String message = this.preferences.getLocalizedString(HomePane.class, "confirmExit.message");
    String title = this.preferences.getLocalizedString(HomePane.class, "confirmExit.title");
    String quit = this.preferences.getLocalizedString(HomePane.class, "confirmExit.quit");
    String doNotQuit = this.preferences.getLocalizedString(HomePane.class, "confirmExit.doNotQuit");

    return SwingTools.showOptionDialog(this, message, title,
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {quit, doNotQuit}, doNotQuit) == JOptionPane.YES_OPTION;
  }

  /**
   * Displays an about dialog.
   */
  public void showAboutDialog() {
    final JTextComponent messagePane = createEditorPane(getAboutMessage());
    messagePane.setOpaque(false);
    // Run a timer that will update About message after a garbage collection
    Timer updateTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          if (messagePane.isShowing()) {
            System.gc();
            messagePane.setText(getAboutMessage());
          }
        }
      });
    updateTimer.start();

    String title = this.preferences.getLocalizedString(HomePane.class, "about.title");
    Icon   icon  = new ImageIcon(HomePane.class.getResource(
        this.preferences.getLocalizedString(HomePane.class, "about.icon")));
    try {
      String close = this.preferences.getLocalizedString(HomePane.class, "about.close");
      String showLibraries = this.preferences.getLocalizedString(HomePane.class, "about.showLibraries");
      List<Library> libraries = this.preferences.getLibraries();
      if (!libraries.isEmpty()) {
        int option = JOptionPane.showOptionDialog(this, messagePane, title,
              JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
              icon, new Object [] {close, showLibraries}, close);
        updateTimer.stop();
        if (option == JOptionPane.NO_OPTION) {
          showLibrariesDialog(libraries);
        }
        return;
      }
    } catch (UnsupportedOperationException ex) {
      // Environment doesn't support libraries
    } catch (IllegalArgumentException ex) {
      // Unknown about.close or about.libraries libraries
    }
    JOptionPane.showMessageDialog(this, messagePane, title, JOptionPane.INFORMATION_MESSAGE, icon);
    updateTimer.stop();
  }

  /**
   * Returns the message displayed in the about dialog.
   */
  private String getAboutMessage() {
    String messageFormat = this.preferences.getLocalizedString(HomePane.class, "about.message");
    String aboutVersion = this.controller.getVersion();
    String javaVersion = System.getProperty("java.version");
    String javaRuntimeName = System.getProperty("java.runtime.name", "Java").split("\\W") [0];
    String javaVendor = System.getProperty("java.vendor", "Oracle");
    String javaVendorUrl = System.getProperty("java.vendor.url");
    if (javaVendorUrl != null) {
      javaVendor = "<a href='" + javaVendorUrl + "'>" + javaVendor + "</a>";
    }
    try {
      String dataModel = System.getProperty("sun.arch.data.model");
      if (dataModel != null) {
        javaVersion += " - <span>" + Integer.parseInt(dataModel) + "bit</span>"; // Glue "bit" to int value to avoid rendering issues in RTL
        if (System.getProperty("os.arch").startsWith("aarch")) {
          javaVersion += " - ARM";
        }
      }
    } catch (NumberFormatException ex) {
      // Don't display data model
    } catch (AccessControlException ex) {
    }
    Runtime runtime = Runtime.getRuntime();
    float usedMemoryGigaByte = Math.max(0.1f, (runtime.totalMemory() - runtime.freeMemory()) / 1073741824f);
    float maxMemoryGigaByte = Math.max(0.1f, (runtime.maxMemory()) / 1073741824f);
    DecimalFormat format = new DecimalFormat("#.#");
    javaVersion += " - " + format.format(usedMemoryGigaByte)
        + " / " + format.format(maxMemoryGigaByte) + " "
        + (Locale.FRENCH.getLanguage().equals(Locale.getDefault().getLanguage()) ? "Go" : "GB");
    String java3dVersion = "<i>not available</i>";
    try {
      if (!Boolean.getBoolean("com.eteks.sweethome3d.no3D")) {
        Map java3dProperties = VirtualUniverse.getProperties();
        java3dVersion = (String)java3dProperties.get("j3d.version");
        if (java3dVersion != null) {
          java3dVersion = java3dVersion.split("\\s") [0];
        }
        String pipeline = (String)java3dProperties.get("j3d.pipeline");
        java3dVersion += " - " + pipeline;
        if ("JOGL".equals(pipeline)) {
          // Call com.jogamp.opengl.JoglVersion.getInstance().getAttribute(java.util.jar.Attributes.Name) by reflection
          Object joglVersionInstance = Class.forName("com.jogamp.opengl.JoglVersion").getMethod("getInstance").invoke(null);
          Method getAttributeMethod = joglVersionInstance.getClass().getMethod("getAttribute", java.util.jar.Attributes.Name.class);
          String joglVersion = (String)getAttributeMethod.invoke(joglVersionInstance, java.util.jar.Attributes.Name.IMPLEMENTATION_VERSION);
          if (joglVersion != null) {
            java3dVersion += " " + joglVersion;
          }
          String joglVendor = (String)getAttributeMethod.invoke(joglVersionInstance, java.util.jar.Attributes.Name.IMPLEMENTATION_VENDOR);
          String joglVendorUrl = (String)getAttributeMethod.invoke(joglVersionInstance, java.util.jar.Attributes.Name.IMPLEMENTATION_URL);
          if (joglVendor != null && joglVendorUrl != null) {
            String joglProvider = this.preferences.getLocalizedString(HomePane.class, "about.java3DProvider",
                "<a href='" + joglVendorUrl + "'>" + joglVendor + "</a>");
            if (joglProvider != null && joglProvider.trim().length() > 0) {
              java3dVersion += "<br>" + joglProvider;
            }
          }
        }
      }
    } catch (Throwable ex) {
      // No Java 3D libraries or refused reflection calls
    }
    return String.format(messageFormat, aboutVersion, javaVersion, java3dVersion, javaRuntimeName, javaVendor);
  }

  /**
   * Returns a component able to display message with active links.
   */
  private JTextComponent createEditorPane(String message) {
    // Use an uneditable editor pane to let user select text in dialog
    JEditorPane messagePane = new JEditorPane("text/html", message);
    messagePane.setEditable(false);
    if (SwingTools.getResolutionScale() != 1) {
      messagePane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
    }
    // Add a listener that displays hyperlinks content in browser
    messagePane.addHyperlinkListener(new HyperlinkListener() {
        public void hyperlinkUpdate(HyperlinkEvent ev) {
          if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            SwingTools.showDocumentInBrowser(ev.getURL());
          }
        }
      });
    return messagePane;
  }

  /**
   * Displays the given <code>libraries</code> in a dialog.
   */
  private void showLibrariesDialog(List<Library> libraries) {
    String title = this.preferences.getLocalizedString(HomePane.class, "libraries.title");
    Map<String, String> librariesLabels = new LinkedHashMap<String, String>();
    librariesLabels.put(UserPreferences.FURNITURE_LIBRARY_TYPE,
        this.preferences.getLocalizedString(HomePane.class, "libraries.furnitureLibraries"));
    librariesLabels.put(UserPreferences.TEXTURES_LIBRARY_TYPE,
        this.preferences.getLocalizedString(HomePane.class, "libraries.texturesLibraries"));
    librariesLabels.put(UserPreferences.LANGUAGE_LIBRARY_TYPE,
        this.preferences.getLocalizedString(HomePane.class, "libraries.languageLibraries"));
    librariesLabels.put(PluginManager.PLUGIN_LIBRARY_TYPE,
        this.preferences.getLocalizedString(HomePane.class, "libraries.plugins"));

    JPanel messagePanel = new JPanel(new GridBagLayout());
    int row = 0;
    for (Map.Entry<String, String> librariesEntry : librariesLabels.entrySet()) {
      final List<Library> typeLibraries = new ArrayList<Library>();
      for (Library library : libraries) {
        if (librariesEntry.getKey().equals(library.getType())) {
          typeLibraries.add(library);
        }
      }
      // If there's some library of the given type
      if (!typeLibraries.isEmpty()) {
        // Add a label
        messagePanel.add(new JLabel(librariesEntry.getValue()), new GridBagConstraints(
            0, row++, 1, 1, 0, 0, GridBagConstraints.LINE_START,
            GridBagConstraints.NONE, new Insets(row == 0 ? 0 : 5, 2, 2, 0), 0, 0));
        // Add a description table
        JTable librariesTable = createLibrariesTable(typeLibraries);
        JScrollPane librariesScrollPane = SwingTools.createScrollPane(librariesTable);
        librariesScrollPane.setPreferredSize(new Dimension(
            Math.round(500 * SwingTools.getResolutionScale()),
            librariesTable.getTableHeader().getPreferredSize().height + librariesTable.getRowHeight() * 5 + 3));
        messagePanel.add(librariesScrollPane, new GridBagConstraints(
            0, row++, 1, 1, 1, 1, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      }
    }
    SwingTools.showMessageDialog(this, messagePanel, title, JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Returns a table describing each library of the given collection.
   */
  private JTable createLibrariesTable(final List<Library> libraries) {
    AbstractTableModel librariesTableModel = new AbstractTableModel() {
        private String [] columnNames = {
            preferences.getLocalizedString(HomePane.class, "libraries.libraryFileColumn"),
            preferences.getLocalizedString(HomePane.class, "libraries.libraryNameColumn"),
            preferences.getLocalizedString(HomePane.class, "libraries.libraryVersionColumn"),
            preferences.getLocalizedString(HomePane.class, "libraries.libraryLicenseColumn"),
            preferences.getLocalizedString(HomePane.class, "libraries.libraryProviderColumn")};

        public int getRowCount() {
          return libraries.size();
        }

        public int getColumnCount() {
          return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
          return columnNames [column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
          Library library = libraries.get(rowIndex);
          switch (columnIndex) {
            case 0 : return library.getLocation();
            case 1 : return library.getName() != null
                ? library.getName()
                : library.getDescription();
            case 2 : return library.getVersion();
            case 3 : return library.getLicense();
            case 4 : return library.getProvider();
            default : throw new IllegalArgumentException();
          }
        }
      };

    final JTable librariesTable = new JTable(librariesTableModel) {
        @Override
        public String getToolTipText(MouseEvent ev) {
          if (columnAtPoint(ev.getPoint()) == 0) {
            int row = rowAtPoint(ev.getPoint());
            if (row >= 0) {
              // Display the full path of the library as a tool tip
              return libraries.get(row).getLocation();
            }
          }
          return null;
        }
      };

    float resolutionScale = SwingTools.getResolutionScale();
    if (resolutionScale != 1) {
      // Adapt row height to specified resolution scale
      librariesTable.setRowHeight(Math.round(librariesTable.getRowHeight() * resolutionScale));
    }
    // Set column widths
    librariesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    TableColumnModel columnModel = librariesTable.getColumnModel();
    int [] columnMinWidths = {15, 20, 7, 50, 20};
    Font defaultFont = new DefaultTableCellRenderer().getFont();
    int charWidth;
    if (defaultFont != null) {
      charWidth = getFontMetrics(defaultFont).getWidths() ['A'];
    } else {
      charWidth = 10;
    }
    for (int i = 0; i < columnMinWidths.length; i++) {
      columnModel.getColumn(i).setPreferredWidth(columnMinWidths [i] * charWidth);
    }

    // Check if it's possible to show a folder
    boolean desktopOpenSupport = false;
    if (OperatingSystem.isJavaVersionGreaterOrEqual("1.6")) {
      try {
        // Check Java SE 6 java.awt.Desktop may be used by reflection to
        // ensure Java SE 5 compatibility
        Class<?> desktopClass = Class.forName("java.awt.Desktop");
        Object desktopInstance = desktopClass.getMethod("getDesktop").invoke(null);
        Class<?> desktopActionClass = Class.forName("java.awt.Desktop$Action");
        Method isSupportedMethod = desktopClass.getMethod("isSupported", desktopActionClass);
        desktopOpenSupport = (Boolean)isSupportedMethod.invoke(desktopInstance,
            desktopActionClass.getMethod("valueOf", String.class).invoke(null, "OPEN"));
      } catch (Exception ex) {
        // For any exception, let's consider simply the open method isn't supported
      }
    }
    final boolean canOpenFolder = desktopOpenSupport || OperatingSystem.isMacOSX() || OperatingSystem.isLinux();

    // Display first column as a link
    columnModel.getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
        {
          if (canOpenFolder) {
            setForeground(Color.BLUE);
          }
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                              boolean isSelected, boolean hasFocus, int row, int column) {
          String location = (String)value;
          try {
            location = new URL(location).getFile().substring(location.lastIndexOf('/') + 1);
          } catch (MalformedURLException ex) {
            // Must be a file
            location = location.substring(location.lastIndexOf(File.separatorChar) + 1);
          }
          super.getTableCellRendererComponent(table, location, isSelected, hasFocus, row, column);
          return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          if (canOpenFolder) {
            // Paint underline
            Insets insets = getInsets();
            g.drawLine(insets.left, getHeight() - 1 - insets.bottom,
                Math.min(getPreferredSize().width, getWidth()) - insets.right,
                getHeight() - 1 - insets.bottom);
          }
        }
      });
    if (canOpenFolder) {
      librariesTable.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent ev) {
            if (librariesTable.columnAtPoint(ev.getPoint()) == 0) {
              int row = librariesTable.rowAtPoint(ev.getPoint());
              if (row >= 0) {
                String location = libraries.get(row).getLocation();
                try {
                  new URL(location);
                } catch (MalformedURLException ex) {
                  showLibraryFolderInSystem(location);
                }
              }
            }
          }
        });
    }
    return librariesTable;
  }

  /**
   * Opens the folder containing the given library in a system window if possible.
   */
  protected void showLibraryFolderInSystem(String libraryLocation) {
    File folder = new File(libraryLocation).getParentFile();

    Object desktopInstance = null;
    Method openMethod = null;
    if (OperatingSystem.isJavaVersionGreaterOrEqual("1.6")) {
      try {
        // Call Java SE 6 java.awt.Desktop open method by reflection to
        // ensure Java SE 5 compatibility
        Class<?> desktopClass = Class.forName("java.awt.Desktop");
        desktopInstance = desktopClass.getMethod("getDesktop").invoke(null);
        Class<?> desktopActionClass = Class.forName("java.awt.Desktop$Action");
        Method isSupportedMethod = desktopClass.getMethod("isSupported", desktopActionClass);
        if ((Boolean)isSupportedMethod.invoke(desktopInstance,
            desktopActionClass.getMethod("valueOf", String.class).invoke(null, "OPEN"))) {
          openMethod = desktopClass.getMethod("open", File.class);
        }
      } catch (Exception ex) {
        // For any exception, let's consider simply the open method isn't available
      }
    }

    try {
      if (openMethod != null) {
        openMethod.invoke(desktopInstance, folder);
      } else if (OperatingSystem.isMacOSX()) {
        Runtime.getRuntime().exec(new String [] {"open", folder.getAbsolutePath()});
      } else { // Linux
        Runtime.getRuntime().exec(new String [] {"xdg-open", folder.getAbsolutePath()});
      }
    } catch (Exception ex2) {
      ex2.printStackTrace();
    }
  }

  /**
   * Displays the given message and returns <code>false</code> if the user
   * doesn't want to be informed of the shown updates anymore.
   * @param updatesMessage the message to display
   * @param showOnlyMessage if <code>false</code> a check box proposing not to display
   *                    again shown updates will be shown.
   */
  public boolean showUpdatesMessage(String updatesMessage,
                                    boolean showOnlyMessage) {
    if (!showOnlyMessage) {
      // Ignore the request if a frame in the program different from the ancestor of this pane
      // is already showing a dialog box
      for (Frame frame : Frame.getFrames()) {
        if (frame != SwingUtilities.getWindowAncestor(this)) {
          for (Window window : frame.getOwnedWindows()) {
            if (window.isShowing() && window instanceof Dialog) {
              return true;
            }
          }
        }
      }
    }

    JPanel updatesPanel = new JPanel(new GridBagLayout());
    final JScrollPane messageScrollPane = new JScrollPane(createEditorPane(updatesMessage));
    messageScrollPane.setPreferredSize(new Dimension(500, 400));
    messageScrollPane.addAncestorListener(new AncestorListener() {
        public void ancestorAdded(AncestorEvent ev) {
          // Force view position to the origin
          messageScrollPane.getViewport().setViewPosition(new Point(0, 0));
        }

        public void ancestorRemoved(AncestorEvent ev) {
        }

        public void ancestorMoved(AncestorEvent ev) {
        }
      });
    updatesPanel.add(messageScrollPane, new GridBagConstraints(
        0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.BOTH, new Insets(0, 0, (int)(5 * SwingTools.getResolutionScale()), 0), 0, 0));

    // Add a check box that lets user choose whether he wants to display the update again at next program launch
    JCheckBox doNotDisplayShownUpdatesCheckBox = new JCheckBox(
        SwingTools.getLocalizedLabelText(this.preferences, HomePane.class, "doNotDisplayShownUpdatesCheckBox.text"));
    if (!OperatingSystem.isMacOSX()) {
      doNotDisplayShownUpdatesCheckBox.setMnemonic(KeyStroke.getKeyStroke(
          this.preferences.getLocalizedString(HomePane.class, "doNotDisplayShownUpdatesCheckBox.mnemonic")).getKeyCode());
    }
    if (!showOnlyMessage) {
      updatesPanel.add(doNotDisplayShownUpdatesCheckBox, new GridBagConstraints(
          0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
          GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }

    SwingTools.showMessageDialog(this, updatesPanel,
        this.preferences.getLocalizedString(HomePane.class, "showUpdatesMessage.title"),
        JOptionPane.PLAIN_MESSAGE, doNotDisplayShownUpdatesCheckBox);
    return !doNotDisplayShownUpdatesCheckBox.isSelected();
  }

  /**
   * Shows a print dialog to print the home displayed by this pane.
   * @return a print task to execute or <code>null</code> if the user canceled print.
   *    The <code>call</code> method of the returned task may throw a
   *    {@link RecorderException RecorderException} exception if print failed
   *    or an {@link InterruptedRecorderException InterruptedRecorderException}
   *    exception if it was interrupted.
   */
  public Callable<Void> showPrintDialog() {
    PageFormat pageFormat = HomePrintableComponent.getPageFormat(this.home.getPrint());
    final PrinterJob printerJob = PrinterJob.getPrinterJob();
    printerJob.setPrintable(new HomePrintableComponent(this.home, this.controller, getFont()), pageFormat);
    String jobName = this.preferences.getLocalizedString(HomePane.class, "print.jobName");
    if (this.home.getName() != null) {
      jobName += " - " + this.controller.getContentManager().getPresentationName(
          this.home.getName(), ContentManager.ContentType.SWEET_HOME_3D);
    }
    printerJob.setJobName(jobName);
    if (printerJob.printDialog()) {
      return new Callable<Void>() {
          private List<Selectable> selectedItems;

          public Void call() throws RecorderException, InterruptedPrinterException {
            this.selectedItems = home.getSelectedItems();
            // Ensure to print 3D view image without selection
            boolean emptySelection = (home.getPrint() == null || home.getPrint().isView3DPrinted())
                && preferences.isEditingIn3DViewEnabled()
                && !selectedItems.isEmpty();
            if (emptySelection) {
              EventQueue.invokeLater(new Runnable() {
                  public void run() {
                    List<Selectable> emptyList = Collections.emptyList();
                    home.setSelectedItems(emptyList);
                  }
                });

              try {
                // Wait selection change is processed
                EventQueue.invokeAndWait(new Runnable() {
                    public void run() {
                    }
                  });
              } catch (InvocationTargetException ex) {
              } catch (InterruptedException ex) {
                throw new InterruptedRecorderException("Print interrupted");
              }
            }

            try {
              printerJob.print();
            } catch (InterruptedPrinterException ex) {
              throw new InterruptedRecorderException("Print interrupted");
            } catch (PrinterException ex) {
              throw new RecorderException("Couldn't print", ex);
            }

            if (emptySelection) {
              EventQueue.invokeLater(new Runnable() {
                  public void run() {
                    home.setSelectedItems(selectedItems);
                  }
                });
            }
            return null;
          }
        };
    } else {
      return null;
    }
  }

  /**
   * Shows a content chooser save dialog to print a home in a PDF file.
   */
  public String showPrintToPDFDialog(String homeName) {
    return this.controller.getContentManager().showSaveDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "printToPDFDialog.title"),
        ContentManager.ContentType.PDF, homeName);
  }

  /**
   * Prints a home to a given PDF file. This method may be overridden
   * to write to another kind of output stream.
   * Caution !!! This method may be called from an other thread than EDT.
   */
  public void printToPDF(String pdfFile) throws RecorderException {
    final List<Selectable> selectedItems = home.getSelectedItems();
    boolean emptySelection = (home.getPrint() == null || home.getPrint().isView3DPrinted())
        && preferences.isEditingIn3DViewEnabled()
        && !selectedItems.isEmpty();
    if (emptySelection) {
      if (EventQueue.isDispatchThread()) {
        System.out.println("Can't avoid printing selection!");
      } else {
        // Print 3D view image without selection
        EventQueue.invokeLater(new Runnable() {
            public void run() {
              List<Selectable> emptySelection = Collections.emptyList();
              home.setSelectedItems(emptySelection);
            }
          });

        try {
          // Wait selection change is processed
          EventQueue.invokeAndWait(new Runnable() {
              public void run() {
              }
            });
        } catch (InvocationTargetException ex) {
        } catch (InterruptedException ex) {
          throw new InterruptedRecorderException("Print interrupted");
        }
      }
    }

    OutputStream outputStream = null;
    boolean printInterrupted = false;
    try {
      outputStream = new FileOutputStream(pdfFile);
      new HomePDFPrinter(this.home, this.preferences, this.controller, getFont())
          .write(outputStream);
    } catch (InterruptedIOException ex) {
      printInterrupted = true;
      throw new InterruptedRecorderException("Print interrupted");
    } catch (IOException ex) {
      throw new RecorderException("Couldn't export to PDF", ex);
    } finally {
      if (emptySelection
          && !EventQueue.isDispatchThread()) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
              home.setSelectedItems(selectedItems);
            }
          });
      }

      try {
        if (outputStream != null) {
          outputStream.close();
        }
        // Delete the file if printing is interrupted
        if (printInterrupted) {
          new File(pdfFile).delete();
        }
      } catch (IOException ex) {
        throw new RecorderException("Couldn't export to PDF", ex);
      }
    }
  }

  /**
   * Shows a content chooser save dialog to export furniture list in a CSV file.
   */
  public String showExportToCSVDialog(String homeName) {
    return this.controller.getContentManager().showSaveDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "exportToCSVDialog.title"),
        ContentManager.ContentType.CSV, homeName);
  }

  /**
   * Exports furniture list to a given CSV file.
   * Caution !!! This method may be called from an other thread than EDT.
   */
  public void exportToCSV(String csvFile) throws RecorderException {
    View view = this.controller.getFurnitureController().getView();
    ExportableView furnitureView;
    if (view instanceof ExportableView
        && ((ExportableView)view).isFormatTypeSupported(ExportableView.FormatType.CSV)) {
      furnitureView = (ExportableView)view;
    } else {
      furnitureView = new FurnitureTable(this.home, this.preferences);
    }

    OutputStream out = null;
    boolean exportInterrupted = false;
    try {
      out = new BufferedOutputStream(new FileOutputStream(csvFile));
      furnitureView.exportData(out, ExportableView.FormatType.CSV, null);
    } catch (InterruptedIOException ex) {
      exportInterrupted = true;
      throw new InterruptedRecorderException("Export to " + csvFile + " interrupted");
    } catch (IOException ex) {
      throw new RecorderException("Couldn't export to CSV in " + csvFile, ex);
    } finally {
      if (out != null) {
        try {
          out.close();
          // Delete the file if exporting is interrupted
          if (exportInterrupted) {
            new File(csvFile).delete();
          }
        } catch (IOException ex) {
          throw new RecorderException("Couldn't export to CSV in " + csvFile, ex);
        }
      }
    }
  }

  /**
   * Shows a content chooser save dialog to export a home plan in a SVG file.
   */
  public String showExportToSVGDialog(String homeName) {
    return this.controller.getContentManager().showSaveDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "exportToSVGDialog.title"),
        ContentManager.ContentType.SVG, homeName);
  }

  /**
   * Exports the plan objects to a given SVG file.
   * Caution !!! This method may be called from an other thread than EDT.
   */
  public void exportToSVG(String svgFile) throws RecorderException {
    View view = this.controller.getPlanController().getView();
    final ExportableView planView;
    if (view instanceof ExportableView
        && ((ExportableView)view).isFormatTypeSupported(ExportableView.FormatType.SVG)) {
      planView = (ExportableView)view;
    } else {
      planView = new PlanComponent(cloneHomeInEventDispatchThread(this.home), this.preferences, null);
    }

    OutputStream outputStream = null;
    boolean exportInterrupted = false;
    try {
      outputStream = new BufferedOutputStream(new FileOutputStream(svgFile));
      planView.exportData(outputStream, ExportableView.FormatType.SVG, null);
    } catch (InterruptedIOException ex) {
      exportInterrupted = true;
      throw new InterruptedRecorderException("Export to " + svgFile + " interrupted");
    } catch (IOException ex) {
      throw new RecorderException("Couldn't export to SVG in " + svgFile, ex);
    } finally {
      if (outputStream != null) {
        try {
          outputStream.close();
          // Delete the file if exporting is interrupted
          if (exportInterrupted) {
            new File(svgFile).delete();
          }
        } catch (IOException ex) {
          throw new RecorderException("Couldn't export to SVG in " + svgFile, ex);
        }
      }
    }
  }

  /**
   * Shows a content chooser save dialog to export a 3D home in a OBJ file.
   */
  public String showExportToOBJDialog(String homeName) {
    homeName = this.controller.getContentManager().showSaveDialog(this,
        this.preferences.getLocalizedString(HomePane.class, "exportToOBJDialog.title"),
        ContentManager.ContentType.OBJ, homeName);

    this.exportAllToOBJ = true;
    List<Selectable> selectedItems = this.home.getSelectedItems();
    if (homeName != null
        && !selectedItems.isEmpty()
        && (selectedItems.size() > 1
            || !(selectedItems.get(0) instanceof Camera))
        && Home.getDimensionLinesSubList(selectedItems).size() < selectedItems.size()) {
      // Ask if all home items or selected items should be exported
      String message = this.preferences.getLocalizedString(HomePane.class, "confirmExportAllToOBJ.message");
      String title = this.preferences.getLocalizedString(HomePane.class, "confirmExportAllToOBJ.title");
      String exportAll = this.preferences.getLocalizedString(HomePane.class, "confirmExportAllToOBJ.exportAll");
      String exportSelection = this.preferences.getLocalizedString(HomePane.class, "confirmExportAllToOBJ.exportSelection");
      String cancel = this.preferences.getLocalizedString(HomePane.class, "confirmExportAllToOBJ.cancel");
      int response = SwingTools.showOptionDialog(this, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
          new Object [] {exportAll, exportSelection, cancel}, exportAll);
      if (response == JOptionPane.NO_OPTION) {
        this.exportAllToOBJ = false;
      } else if (response != JOptionPane.YES_OPTION) {
        return null;
      }
    }
    return homeName;
  }

  /**
   * Exports the objects of the 3D view to the given OBJ file.
   * Caution !!! This method may be called from an other thread than EDT.
   */
  public void exportToOBJ(String objFile) throws RecorderException {
    View view3D = this.controller.getHomeController3D().getView();
    Object3DFactory object3dFactory = view3D instanceof HomeComponent3D
        ? ((HomeComponent3D)view3D).getObject3DFactory()
        : new Object3DBranchFactory();
    exportToOBJ(objFile, object3dFactory);
  }

  /**
   * Exports to an OBJ file the objects of the 3D view created with the given factory.
   * Caution !!! This method may be called from an other thread than EDT.
   */
  protected void exportToOBJ(String objFile, Object3DFactory object3dFactory) throws RecorderException {
    String header = this.preferences != null
        ? this.preferences.getLocalizedString(HomePane.class,
                                              "exportToOBJ.header", new Date())
        : "";

    // Use a clone of home to ignore selection and for thread safety
    OBJExporter.exportHomeToFile(cloneHomeInEventDispatchThread(this.home),
        objFile, header, this.exportAllToOBJ, object3dFactory);
  }

  /**
   * Returns a clone of the given <code>home</code> safely cloned in the EDT.
   */
  private Home cloneHomeInEventDispatchThread(final Home home) throws RecorderException {
    if (EventQueue.isDispatchThread()) {
      return home.clone();
    } else {
      try {
        final AtomicReference<Home> clonedHome = new AtomicReference<Home>();
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {
              clonedHome.set(home.clone());
            }
          });
        return clonedHome.get();
      } catch (InterruptedException ex) {
        throw new InterruptedRecorderException(ex.getMessage());
      } catch (InvocationTargetException ex) {
        throw new RecorderException("Couldn't clone home", ex.getCause());
      }
    }
  }

  /**
   * Export to OBJ in a separate class to be able to run HomePane without Java 3D classes.
   */
  private static class OBJExporter {
    public static void exportHomeToFile(Home home, String objFile, String header,
                                        boolean exportAllToOBJ, Object3DFactory object3dFactory) throws RecorderException {
      OBJWriter writer = null;
      boolean exportInterrupted = false;
      try {
        writer = new OBJWriter(objFile, header, -1);

        List<Selectable> exportedItems = new ArrayList<Selectable>(exportAllToOBJ
            ? home.getSelectableViewableItems()
            : home.getSelectedItems());
        // Search furniture in groups
        List<Selectable> furnitureInGroups = new ArrayList<Selectable>();
        for (Iterator<Selectable> it = exportedItems.iterator(); it.hasNext();) {
          Selectable selectable = (Selectable)it.next();
          if (selectable instanceof HomeFurnitureGroup) {
            it.remove();
            for (HomePieceOfFurniture piece : ((HomeFurnitureGroup)selectable).getAllFurniture()) {
              if (!(piece instanceof HomeFurnitureGroup)) {
                furnitureInGroups.add(piece);
              }
            }
          }
        }
        exportedItems.addAll(furnitureInGroups);

        List<Selectable> emptySelection = Collections.emptyList();
        home.setSelectedItems(emptySelection);
        if (exportAllToOBJ) {
          // Create a not alive new ground to be able to explore its coordinates without setting capabilities
          Rectangle2D homeBounds = getExportedHomeBounds(home);
          if (homeBounds != null) {
            Ground3D groundNode = new Ground3D(home,
                (float)homeBounds.getX(), (float)homeBounds.getY(),
                (float)homeBounds.getWidth(), (float)homeBounds.getHeight(), true);
            writer.writeNode(groundNode, "ground");
          }
        } else if (home.isAllLevelsSelection()) {
          // Make viewable levels visible when all levels are selected
          for (Level level : home.getLevels()) {
            if (level.isViewable()) {
              level.setVisible(true);
            }
          }
        }

        // Write 3D objects
        int i = 0;
        for (Selectable item : exportedItems) {
          // Create a not alive new node to be able to explore its coordinates without setting capabilities
          Node node = (Node)object3dFactory.createObject3D(home, item, true);
          if (node != null) {
            if (item instanceof HomePieceOfFurniture) {
              writer.writeNode(node);
            } else if (!(item instanceof DimensionLine)) {
              writer.writeNode(node, item.getClass().getSimpleName().toLowerCase() + "_" + ++i);
            }
          }
        }
      } catch (InterruptedIOException ex) {
        exportInterrupted = true;
        throw new InterruptedRecorderException("Export to " + objFile + " interrupted");
      } catch (IOException ex) {
        throw new RecorderException("Couldn't export to OBJ in " + objFile, ex);
      } finally {
        if (writer != null) {
          try {
            writer.close();
            // Delete the file if exporting is interrupted
            if (exportInterrupted) {
              new File(objFile).delete();
            }
          } catch (IOException ex) {
            throw new RecorderException("Couldn't export to OBJ in " + objFile, ex);
          }
        }
      }
    }

    /**
     * Returns <code>home</code> bounds.
     */
    private static Rectangle2D getExportedHomeBounds(Home home) {
      // Compute bounds that include walls and furniture
      Rectangle2D homeBounds = updateObjectsBounds(null, home.getWalls());
      for (HomePieceOfFurniture piece : getVisibleFurniture(home.getFurniture())) {
        for (float [] point : piece.getPoints()) {
          if (homeBounds == null) {
            homeBounds = new Rectangle2D.Float(point [0], point [1], 0, 0);
          } else {
            homeBounds.add(point [0], point [1]);
          }
        }
      }
      return updateObjectsBounds(homeBounds, home.getRooms());
    }

    /**
     * Returns all the visible pieces in the given <code>furniture</code>.
     */
    private static List<HomePieceOfFurniture> getVisibleFurniture(List<HomePieceOfFurniture> furniture) {
      List<HomePieceOfFurniture> visibleFurniture = new ArrayList<HomePieceOfFurniture>(furniture.size());
      for (HomePieceOfFurniture piece : furniture) {
        if (piece.isVisible()
            && (piece.getLevel() == null
                || piece.getLevel().isViewable())) {
          if (piece instanceof HomeFurnitureGroup) {
            visibleFurniture.addAll(getVisibleFurniture(((HomeFurnitureGroup)piece).getFurniture()));
          } else {
            visibleFurniture.add(piece);
          }
        }
      }
      return visibleFurniture;
    }

    /**
     * Updates <code>objectBounds</code> to include the bounds of <code>items</code>.
     */
    private static Rectangle2D updateObjectsBounds(Rectangle2D objectBounds,
                                            Collection<? extends Selectable> items) {
      for (Selectable item : items) {
        if (!(item instanceof Elevatable)
            || ((Elevatable)item).getLevel() == null
            || ((Elevatable)item).getLevel().isViewableAndVisible()) {
          for (float [] point : item.getPoints()) {
            if (objectBounds == null) {
              objectBounds = new Rectangle2D.Float(point [0], point [1], 0, 0);
            } else {
              objectBounds.add(point [0], point [1]);
            }
          }
        }
      }
      return objectBounds;
    }
  }

  /**
   * Displays a dialog that let user choose whether he wants to delete
   * the selected furniture from catalog or not.
   * @return <code>true</code> if user confirmed to delete.
   */
  public boolean confirmDeleteCatalogSelection() {
    // Retrieve displayed text in buttons and message
    String message = this.preferences.getLocalizedString(HomePane.class, "confirmDeleteCatalogSelection.message");
    String title = this.preferences.getLocalizedString(HomePane.class, "confirmDeleteCatalogSelection.title");
    String delete = this.preferences.getLocalizedString(HomePane.class, "confirmDeleteCatalogSelection.delete");
    String cancel = this.preferences.getLocalizedString(HomePane.class, "confirmDeleteCatalogSelection.cancel");

    return SwingTools.showOptionDialog(this, message, title,
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
        new Object [] {delete, cancel}, cancel) == JOptionPane.OK_OPTION;
  }

  /**
   * Displays a dialog that lets the user choose a name for the current camera.
   * @return the chosen name or <code>null</code> if the user canceled.
   */
  public String showStoreCameraDialog(String cameraName) {
    // Retrieve displayed text in dialog
    String message = this.preferences.getLocalizedString(HomePane.class, "showStoreCameraDialog.message");
    String title = this.preferences.getLocalizedString(HomePane.class, "showStoreCameraDialog.title");

    List<Camera> storedCameras = this.home.getStoredCameras();
    JComponent cameraNameChooser;
    JTextComponent cameraNameTextComponent;
    if (storedCameras.isEmpty()) {
      cameraNameChooser = cameraNameTextComponent = new JTextField(cameraName, 20);
    } else {
      // If cameras are already stored in home propose an editable combo box to user
      // to let him choose more easily an existing one if he want to overwrite it
      String [] storedCameraNames = new String [storedCameras.size()];
      for (int i = 0; i < storedCameraNames.length; i++) {
        storedCameraNames [i] = storedCameras.get(i).getName();
      }
      JComboBox cameraNameComboBox = new JComboBox(storedCameraNames);
      cameraNameComboBox.setEditable(true);
      cameraNameComboBox.getEditor().setItem(cameraName);
      Component editorComponent = cameraNameComboBox.getEditor().getEditorComponent();
      if (editorComponent instanceof JTextComponent) {
        cameraNameTextComponent = (JTextComponent)editorComponent;
        cameraNameChooser = cameraNameComboBox;
      } else {
        cameraNameChooser = cameraNameTextComponent = new JTextField(cameraName, 20);
      }
    }
    if (!OperatingSystem.isMacOSXLeopardOrSuperior()) {
      SwingTools.addAutoSelectionOnFocusGain(cameraNameTextComponent);
    }
    JPanel cameraNamePanel = new JPanel(new BorderLayout(2, 2));
    cameraNamePanel.add(new JLabel(message), BorderLayout.NORTH);
    cameraNamePanel.add(cameraNameChooser, BorderLayout.SOUTH);
    if (SwingTools.showConfirmDialog(this, cameraNamePanel,
        title, cameraNameTextComponent) == JOptionPane.OK_OPTION) {
      cameraName = cameraNameTextComponent.getText().trim();
      if (cameraName.length() > 0) {
        return cameraName;
      }
    }

    return null;
  }

  /**
   * Displays a dialog showing the list of cameras stored in home
   * and returns the ones selected by the user to be deleted.
   */
  public List<Camera> showDeletedCamerasDialog() {
    // Build stored cameras list
    List<Camera> storedCameras = this.home.getStoredCameras();
    final List<Camera> selectedCameras = new ArrayList<Camera>();
    final JList camerasList = new JList(storedCameras.toArray());
    camerasList.setCellRenderer(new ListCellRenderer() {
        private JCheckBox cameraCheckBox = new JCheckBox();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
          this.cameraCheckBox.setText(((Camera)value).getName());
          this.cameraCheckBox.setSelected(selectedCameras.contains(value));
          this.cameraCheckBox.setOpaque(true);
          if (isSelected && list.hasFocus()) {
            this.cameraCheckBox.setBackground(list.getSelectionBackground());
            this.cameraCheckBox.setForeground(list.getSelectionForeground());
          }
          else {
            this.cameraCheckBox.setBackground(list.getBackground());
            this.cameraCheckBox.setForeground(list.getForeground());
          }
          return this.cameraCheckBox;
        }
      });
    camerasList.getInputMap().put(KeyStroke.getKeyStroke("pressed SPACE"), "toggleSelection");
    final AbstractAction toggleSelectionAction = new AbstractAction() {
        public void actionPerformed(ActionEvent ev) {
          Camera selectedCamera = (Camera)camerasList.getSelectedValue();
          if (selectedCamera != null) {
            int index = selectedCameras.indexOf(selectedCamera);
            if (index >= 0) {
              selectedCameras.remove(index);
            } else {
              selectedCameras.add(selectedCamera);
            }
            camerasList.repaint();
          }
        }
      };
    camerasList.getActionMap().put("toggleSelection", toggleSelectionAction);
    camerasList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent ev) {
          toggleSelectionAction.actionPerformed(null);
        }
      });
    camerasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    camerasList.setSelectedIndex(0);

    // Retrieve displayed text in dialog
    String message = this.preferences.getLocalizedString(HomePane.class, "showDeletedCamerasDialog.message");
    String title = this.preferences.getLocalizedString(HomePane.class, "showDeletedCamerasDialog.title");

    JPanel camerasPanel = new JPanel(new BorderLayout(0, (int)(5 * SwingTools.getResolutionScale())));
    camerasPanel.add(new JLabel(message), BorderLayout.NORTH);
    camerasPanel.add(SwingTools.createScrollPane(camerasList), BorderLayout.CENTER);

    if (SwingTools.showConfirmDialog(this, camerasPanel, title, camerasList) == JOptionPane.OK_OPTION
        && selectedCameras.size() > 0) {
      String confirmMessage = this.preferences.getLocalizedString(HomePane.class, "confirmDeleteCameras.message");
      String delete = this.preferences.getLocalizedString(HomePane.class, "confirmDeleteCameras.delete");
      String cancel = this.preferences.getLocalizedString(HomePane.class, "confirmDeleteCameras.cancel");
      if (SwingTools.showOptionDialog(this, confirmMessage, title,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
            new Object [] {delete, cancel}, cancel) == JOptionPane.OK_OPTION) {
        return selectedCameras;
      }
    }
    return null;
  }

  /**
   * Adds a listener to clipboard to follow the changes of its content.
   */
  private void addClipboardListener() {
    if (!OperatingSystem.isMacOSX() || OperatingSystem.isJavaVersionGreaterOrEqual("1.8.0_60")) {
      try {
        final FlavorListener flavorListener = new FlavorListener() {
            public void flavorsChanged(FlavorEvent ev) {
              checkClipboardContainsHomeItemsOrFiles();
            }
          };
        flavorListener.flavorsChanged(null);
        // Bind listener after access control check and only
        // when needed to avoid permanent link to global clipboard
        addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent event) {
              getToolkit().getSystemClipboard().addFlavorListener(flavorListener);
            }

            public void ancestorRemoved(AncestorEvent event) {
              getToolkit().getSystemClipboard().removeFlavorListener(flavorListener);
            }

            public void ancestorMoved(AncestorEvent event) {
            }
          });
      } catch (AccessControlException ex) {
        // If clipboard can't be accessed, update clipboardEmpty only when explicit copy actions are performed
      }
    }
  }

  /**
   * Returns <code>true</code> if clipboard doesn't contain data that
   * components are able to handle.
   */
  public boolean isClipboardEmpty() {
    if (OperatingSystem.isMacOSX() && !OperatingSystem.isJavaVersionGreaterOrEqual("1.8.0_60")) {
      try {
        checkClipboardContainsHomeItemsOrFiles();
      } catch (AccessControlException ex) {
        // If clipboard can't be accessed, update clipboardEmpty only when explicit copy actions are performed
      }
    }
    return this.clipboardEmpty;
  }

  private void checkClipboardContainsHomeItemsOrFiles() {
    Clipboard clipboard = getToolkit().getSystemClipboard();
    this.clipboardEmpty = !(clipboard.isDataFlavorAvailable(HomeTransferableList.HOME_FLAVOR)
        || clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor));
  }

  /**
   * Returns the list of selectable items that are currently in clipboard
   * or <code>null</code> if clipboard doesn't contain any selectable item.
   */
  @SuppressWarnings("unchecked")
  public List<Selectable> getClipboardItems() {
    try {
      Clipboard clipboard = getToolkit().getSystemClipboard();
      if (clipboard.isDataFlavorAvailable(HomeTransferableList.HOME_FLAVOR)) {
        return (List<Selectable>)clipboard.getData(HomeTransferableList.HOME_FLAVOR);
      }
    } catch (AccessControlException ex) {
    } catch (UnsupportedFlavorException ex) {
    } catch (IOException ex) {
    }
    return null;
  }

  /**
   * Execute <code>runnable</code> asynchronously in the thread
   * that manages toolkit events.
   */
  public void invokeLater(Runnable runnable) {
    EventQueue.invokeLater(runnable);
  }

  /**
   * A Swing action adapter to a plug-in action.
   */
  private class ActionAdapter implements Action {
    private PluginAction               pluginAction;
    private SwingPropertyChangeSupport propertyChangeSupport;

    private ActionAdapter(PluginAction pluginAction) {
      this.pluginAction = pluginAction;
      this.propertyChangeSupport = new SwingPropertyChangeSupport(this);
      this.pluginAction.addPropertyChangeListener(new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent ev) {
            String propertyName = ev.getPropertyName();
            Object oldValue = ev.getOldValue();
            Object newValue = ev.getNewValue();
            if (PluginAction.Property.ENABLED.name().equals(propertyName)) {
              propertyChangeSupport.firePropertyChange(
                  new PropertyChangeEvent(ev.getSource(), "enabled", oldValue, newValue));
            } else {
              // In case a property value changes, fire the new value decorated in subclasses
              // unless new value is null (most Swing listeners don't check new value is null !)
              if (newValue != null) {
                if (PluginAction.Property.NAME.name().equals(propertyName)) {
                  propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(),
                      Action.NAME, oldValue, newValue));
                } else if (PluginAction.Property.SHORT_DESCRIPTION.name().equals(propertyName)) {
                  propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(),
                      Action.SHORT_DESCRIPTION, oldValue, newValue));
                } else if (PluginAction.Property.MNEMONIC.name().equals(propertyName)) {
                  propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(),
                      Action.MNEMONIC_KEY,
                      oldValue != null
                          ? new Integer((Character)oldValue)
                          : null, newValue));
                } else if (PluginAction.Property.SMALL_ICON.name().equals(propertyName)) {
                  propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(),
                      Action.SMALL_ICON,
                      oldValue != null
                         ? IconManager.getInstance().getIcon((Content)oldValue, DEFAULT_SMALL_ICON_HEIGHT, HomePane.this)
                         : null, newValue));
                } else {
                  propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(ev.getSource(),
                      propertyName, oldValue, newValue));
                }
              }
            }
          }
        });
    }

    public void actionPerformed(ActionEvent ev) {
      this.pluginAction.execute();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
      this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
      this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public Object getValue(String key) {
      if (NAME.equals(key)) {
        return this.pluginAction.getPropertyValue(PluginAction.Property.NAME);
      } else if (SHORT_DESCRIPTION.equals(key)) {
        return this.pluginAction.getPropertyValue(PluginAction.Property.SHORT_DESCRIPTION);
      } else if (SMALL_ICON.equals(key)) {
        Content smallIcon = (Content)this.pluginAction.getPropertyValue(PluginAction.Property.SMALL_ICON);
        return smallIcon != null
            ? IconManager.getInstance().getIcon(smallIcon, DEFAULT_SMALL_ICON_HEIGHT, HomePane.this)
            : null;
      } else if (MNEMONIC_KEY.equals(key)) {
        Character mnemonic = (Character)this.pluginAction.getPropertyValue(PluginAction.Property.MNEMONIC);
        return mnemonic != null
            ? new Integer(mnemonic)
            : null;
      } else if (PluginAction.Property.TOOL_BAR.name().equals(key)) {
        return this.pluginAction.getPropertyValue(PluginAction.Property.TOOL_BAR);
      } else if (PluginAction.Property.MENU.name().equals(key)) {
        return this.pluginAction.getPropertyValue(PluginAction.Property.MENU);
      } else {
        return null;
      }
    }

    public void putValue(String key, Object value) {
      if (NAME.equals(key)) {
        this.pluginAction.putPropertyValue(PluginAction.Property.NAME, value);
      } else if (SHORT_DESCRIPTION.equals(key)) {
        this.pluginAction.putPropertyValue(PluginAction.Property.SHORT_DESCRIPTION, value);
      } else if (SMALL_ICON.equals(key)) {
        // Ignore icon change
      } else if (MNEMONIC_KEY.equals(key)) {
        this.pluginAction.putPropertyValue(PluginAction.Property.MNEMONIC,
            new Character((char)((Integer)value).intValue()));
      } else if (PluginAction.Property.TOOL_BAR.name().equals(key)) {
        this.pluginAction.putPropertyValue(PluginAction.Property.TOOL_BAR, value);
      } else if (PluginAction.Property.MENU.name().equals(key)) {
        this.pluginAction.putPropertyValue(PluginAction.Property.MENU, value);
      }
    }

    public boolean isEnabled() {
      return this.pluginAction.isEnabled();
    }

    public void setEnabled(boolean enabled) {
      this.pluginAction.setEnabled(enabled);
    }
  }

  /**
   * An object able to format a selectable item.
   */
  private abstract interface SelectableFormat<T extends Selectable> {
    public abstract String format(T item);
  }

  /**
   * A popup listener that stores the location of the mouse.
   */
  private static abstract class PopupMenuListenerWithMouseLocation implements PopupMenuListener {
    private Point mouseLocation;
    private Point lastMouseMoveLocation;

    public PopupMenuListenerWithMouseLocation(JComponent component) {
      component.addMouseMotionListener(new MouseMotionAdapter() {
          @Override
          public void mouseMoved(MouseEvent ev) {
            lastMouseMoveLocation = ev.getPoint();
          }
        });
      component.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseExited(MouseEvent ev) {
            lastMouseMoveLocation = null;
          }

          @Override
          public void mouseEntered(MouseEvent ev) {
            lastMouseMoveLocation = ev.getPoint();
          }
       });
    }

    protected Point getMouseLocation() {
      return this.mouseLocation;
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent ev) {
      // Copy last mouse move location in case mouseExited is called while popup menu is displayed
      this.mouseLocation = this.lastMouseMoveLocation;
    }
  }
}

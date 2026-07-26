package settlement.room.spirit.shrine;

import java.io.IOException;
import settlement.path.AVAILABILITY;
import settlement.room.main.Room;
import settlement.room.main.RoomBlueprintImp;
import settlement.room.main.TmpArea;
import settlement.room.main.furnisher.Furnisher;
import settlement.room.main.furnisher.FurnisherItem;
import settlement.room.main.furnisher.FurnisherItemTile;
import settlement.room.main.furnisher.FurnisherStat;
import settlement.room.main.util.RoomInit;
import settlement.room.main.util.RoomInitData;
import settlement.room.spirit.shrine.ROOM_SHRINE;
import settlement.room.spirit.shrine.ShrineInstance;
import settlement.room.sprite.RoomSprite;
import settlement.room.sprite.RoomSprite1x1;
import settlement.room.sprite.RoomSpriteBoxN;
import settlement.room.sprite.RoomSpriteTex;
import settlement.room.sprite.RoomSpriteXxX;
import snake2d.SPRITE_RENDERER;
import snake2d.util.datatypes.DIR;
import snake2d.util.file.Json;
import thalassicus.capacity.ThalServiceFurnisherStat;
import util.rendering.RenderData;
import util.rendering.ShadowBatch;

final class Constructor extends Furnisher {
   private final ROOM_SHRINE blue;
   // MOD START
   // Incorrectly used FurnisherStatI instead of FurnisherStatServices.
   final FurnisherStat services;
   // MOD END
   static final int codeService = 1;
   static final int codeFire = 2;

   protected Constructor(ROOM_SHRINE blue, RoomInitData init) throws IOException {
      super(init, 1, 1);
      this.blue = blue;
       // MOD START
      this.services = new FurnisherStat.FurnisherStatServices(this, this.blue);
       // MOD END
      Json sp = init.data().json("SPRITES");
      RoomSprite sClear = RoomSprite.DUMMY;
      final RoomSprite sScribble = new RoomSpriteTex(sp, "ALTAR_FLOOR_TEXTURE") {
         RoomSprite sc1;
         RoomSprite sc2;

         {
            this.sc1 = new RoomSpriteTex(sp, "ALTAR_FLOOR_SCRIBBLE1_TEXTURE");
            this.sc2 = new RoomSpriteTex(sp, "ALTAR_FLOOR_SCRIBBLE2_TEXTURE");
         }

         @Override
         public boolean render(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade, boolean isCandle) {
            return false;
         }

         @Override
         public void renderBelow(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade) {
            it.ranOffset(1, 0);
            super.render(r, s, data, it, degrade, this.rotates);
            it.ranOffset(2, 0);
            this.sc1.render(r, s, 0, it, degrade, false);
            it.ranOffset(3, 0);
            this.sc2.render(r, s, 0, it, degrade, false);
         }
      };
      final RoomSprite sStairs1 = new SStairs(1, sp);
      final RoomSprite sAltarNormal = (new RoomSpriteBoxN(sp, "ALTAR_BOX") {
         @Override
         protected boolean joins(int tx, int ty, int rx, int ry, DIR d, FurnisherItem item) {
            return item.sprite(rx, ry) != null && item.sprite(rx, ry).sData() == 99;
         }

         @Override
         public void renderBelow(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade) {
            sStairs1.renderBelow(r, s, 15, it, degrade);
            sScribble.renderBelow(r, s, 0, it, degrade);
         }
      }).sData(99);
      FurnisherItemTile aa = new FurnisherItemTile(this, sAltarNormal, AVAILABILITY.SOLID, false);
      FurnisherItemTile __ = new FurnisherItemTile(this, sClear, AVAILABILITY.ROOM, false);
      FurnisherItemTile _x = new FurnisherItemTile(this, sScribble, AVAILABILITY.ROOM, false);
      FurnisherItemTile oo = new FurnisherItemTile(this, sStairs1, AVAILABILITY.ROOM, false);
      RoomSprite sAltarTorch = (new SStairs(1, sp) {
         final RoomSprite1x1 torch;

         {
            this.torch = new RoomSprite1x1(sp, "TORCH_1X1");
         }

         @Override
         public byte getData2(int tx, int ty, int rx, int ry, FurnisherItem item, int itemRan) {
            return this.torch.getData(tx, ty, rx, ry, item, itemRan);
         }

         @Override
         public boolean render(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade, boolean isCandle) {
            this.torch.render(r, s, this.getData2(it), it, degrade, false);
            return false;
         }
      }).sData(1);
      RoomSprite sAltarScrib = new SScribbleAltar(2, sp, sStairs1);
      RoomSprite1x1 sAltarReliefSmall = new RoomSprite1x1(sp, "EMBLEM_1X1") {
         @Override
         protected boolean joins(int tx, int ty, int rx, int ry, DIR d, FurnisherItem item) {
            return d.orthoID() == item.rotation;
         }
      };
      RoomSprite sAltarReliefSmall1 = new ASmall(new SScribbleAltar(1, sp, sAltarNormal), sAltarNormal, sAltarReliefSmall, -2)
         .sData(99);
      RoomSprite sAltarReliefSmall2 = new ASmall(new SScribbleAltar(1, sp, sAltarNormal), sAltarNormal, sAltarReliefSmall, 2).sData(99);
      FurnisherItemTile tt = new FurnisherItemTile(this, sAltarTorch, AVAILABILITY.SOLID, false).setData(2);
      FurnisherItemTile xx = new FurnisherItemTile(this, sAltarScrib, AVAILABILITY.ROOM, false);
      FurnisherItemTile c1 = new FurnisherItemTile(this, sAltarReliefSmall1, AVAILABILITY.SOLID, false);
      FurnisherItemTile c2 = new FurnisherItemTile(this, sAltarReliefSmall2, AVAILABILITY.SOLID, false);
      this.make(
         new FurnisherItemTile[][]{
            {_x, _x, __, __, _x, _x},
            {oo, oo, oo, oo, oo, oo},
            {oo, xx, aa, aa, xx, oo},
            {oo, tt, c1, c2, tt, oo},
            {oo, xx, aa, aa, xx, oo},
            {oo, oo, oo, oo, oo, oo}
         }
      );
      this.make(
         new FurnisherItemTile[][]{
            {_x, _x, __, __, _x, _x},
            {oo, oo, oo, oo, oo, oo},
            {oo, xx, aa, aa, xx, oo},
            {oo, tt, c1, c2, tt, oo},
            {oo, xx, aa, aa, xx, oo},
            {oo, oo, oo, oo, oo, oo},
            {_x, _x, __, __, _x, _x}
         }
      );
      this.make(
         new FurnisherItemTile[][]{
            {_x, _x, __, __, __, __, _x, _x},
            {_x, oo, oo, oo, oo, oo, oo, _x},
            {__, oo, xx, aa, aa, xx, oo, __},
            {__, tt, xx, c1, c2, xx, tt, __},
            {__, oo, xx, aa, aa, xx, oo, __},
            {_x, oo, oo, oo, oo, oo, oo, _x},
            {_x, _x, __, __, __, __, _x, _x}
         }
      );
      sAltarTorch = (new SStairs(1, sp) {
         final RoomSprite1x1 torch;

         {
            this.torch = new RoomSprite1x1(sp, "TORCH_1X1");
         }

         @Override
         public byte getData2(int tx, int ty, int rx, int ry, FurnisherItem item, int itemRan) {
            return this.torch.getData(tx, ty, rx, ry, item, itemRan);
         }

         @Override
         public boolean render(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade, boolean isCandle) {
            this.torch.render(r, s, this.getData2(it), it, degrade, false);
            return false;
         }
      }).sData(1);
      sAltarScrib = (new RoomSpriteXxX(sp, "EMBLEM_2X2", 2) {
         @Override
         public boolean render(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade, boolean isCandle) {
            sAltarNormal.render(r, s, 15, it, degrade, isCandle);
            super.render(r, s, data, it, degrade, isCandle);
            return false;
         }
      }).sData(99);
      FurnisherItemTile ttx = new FurnisherItemTile(this, sAltarTorch, AVAILABILITY.SOLID, false).setData(2);
      FurnisherItemTile o2 = new FurnisherItemTile(this, new SStairs(2, sp), AVAILABILITY.ROOM, false);
      FurnisherItemTile xxx = new FurnisherItemTile(this, new SScribbleAltar(2, sp, sStairs1), AVAILABILITY.ROOM, false);
      tt = new FurnisherItemTile(this, sAltarScrib, AVAILABILITY.SOLID, false);
      this.make(
         new FurnisherItemTile[][]{
            {_x, _x, _x, __, __, __, __, _x, _x, _x},
            {_x, oo, oo, oo, oo, oo, oo, oo, oo, _x},
            {_x, oo, xxx, aa, aa, aa, aa, xxx, oo, _x},
            {__, oo, xxx, aa, tt, tt, aa, xxx, oo, __},
            {__, oo, xxx, aa, tt, tt, aa, xxx, oo, __},
            {_x, oo, xxx, aa, aa, aa, aa, xxx, oo, _x},
            {_x, ttx, oo, oo, oo, oo, oo, oo, ttx, _x},
            {_x, _x, _x, __, __, __, __, _x, _x, _x}
         }
      );
      this.make(
         new FurnisherItemTile[][]{
            {_x, _x, _x, _x, __, __, __, __, _x, _x, _x, _x},
            {_x, oo, oo, oo, oo, oo, oo, oo, oo, oo, oo, _x},
            {_x, oo, o2, o2, o2, o2, o2, o2, o2, o2, oo, _x},
            {_x, oo, o2, xxx, aa, aa, aa, aa, xxx, o2, oo, _x},
            {__, oo, o2, xxx, aa, tt, tt, aa, xxx, o2, oo, __},
            {__, oo, o2, xxx, aa, tt, tt, aa, xxx, o2, oo, __},
            {_x, oo, o2, xxx, aa, aa, aa, aa, xxx, o2, oo, _x},
            {_x, oo, o2, o2, o2, o2, o2, o2, o2, o2, oo, _x},
            {_x, ttx, oo, oo, oo, oo, oo, oo, oo, oo, ttx, _x},
            {_x, _x, _x, _x, __, __, __, __, _x, _x, _x, _x}
         }
      );
      this.flush(1, 3);
   }

   private void make(FurnisherItemTile[][] ttt) {
      int am = 0;
      FurnisherItemTile[][] var6 = ttt;
      int var5 = ttt.length;

      for (int sp = 0; sp < var5; sp++) {
         FurnisherItemTile[] tt = var6[sp];
         FurnisherItemTile[] var10 = tt;
         int var9 = tt.length;

         for (int var8 = 0; var8 < var9; var8++) {
            FurnisherItemTile t = var10[var8];
            if (t.availability.player > 0.0) {
               am++;
            }
         }
      }

      double cost = am * 0.75;
      new FurnisherItem(ttt, cost, am);
   }

   @Override
   public boolean usesArea() {
      return false;
   }

   @Override
   public boolean mustBeIndoors() {
      return false;
   }

   @Override
   public Room create(TmpArea area, RoomInit init) {
      return new ShrineInstance(this.blue, area, init);
   }

   @Override
   public RoomBlueprintImp blue() {
      return this.blue;
   }

   private static class ASmall extends RoomSpriteBoxN {
      private final RoomSprite1x1 sAltarReliefSmall;
      private final RoomSprite s2;
      private int off;

      public ASmall(RoomSprite s2, RoomSprite saltar, RoomSprite1x1 sAltarReliefSmall, int off) throws IOException {
         super(saltar);
         this.sAltarReliefSmall = sAltarReliefSmall;
         this.s2 = s2;
         this.off = off;
      }

      @Override
      protected boolean joins(int tx, int ty, int rx, int ry, DIR d, FurnisherItem item) {
         return item.sprite(rx, ry) != null && item.sprite(rx, ry).sData() == 99;
      }

      @Override
      public void renderBelow(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade) {
         this.s2.renderBelow(r, s, 15, it, degrade);
      }

      @Override
      public boolean render(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade, boolean isCandle) {
         super.render(r, s, data, it, degrade, isCandle);
         DIR dd = this.sAltarReliefSmall.rot(this.getData2(it)).next(-this.off);
         it.setOff(dd.x() * 32, dd.y() * 32);
         this.sAltarReliefSmall.render(r, s, this.getData2(it), it, degrade, false);
         return false;
      }

      @Override
      public byte getData2(int tx, int ty, int rx, int ry, FurnisherItem item, int itemRan) {
         return this.sAltarReliefSmall.getData(tx, ty, rx, ry, item, itemRan);
      }
   }

   private static class SScribbleAltar extends RoomSpriteTex {
      RoomSprite sc1;
      RoomSprite sc2;
      final RoomSprite sAltarNormal;

      public SScribbleAltar(int level, Json sp, RoomSprite sAltarNormal) throws IOException {
         super(sp, "ALTAR_FLOOR_TEXTURE");
         this.sData(level);
         this.sc1 = new RoomSpriteTex(sp, "ALTAR_FLOOR_SCRIBBLE1_TEXTURE");
         this.sc2 = new RoomSpriteTex(sp, "ALTAR_FLOOR_SCRIBBLE2_TEXTURE");
         this.sAltarNormal = sAltarNormal;
      }

      @Override
      public boolean render(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade, boolean isCandle) {
         return false;
      }

      @Override
      public void renderBelow(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade) {
         this.sAltarNormal.renderBelow(r, s, 15, it, degrade);
         it.ranOffset(1, 0);
         super.render(r, s, data, it, degrade, false);
         it.ranOffset(2, 0);
         this.sc1.render(r, s, 0, it, degrade, false);
         it.ranOffset(3, 0);
         this.sc2.render(r, s, 0, it, degrade, false);
      }
   }

   private static class SStairs extends RoomSpriteBoxN {
      public SStairs(int level, Json sp) throws IOException {
         super(sp, "INNER_BOX");
         this.sData(level);
      }

      @Override
      protected boolean joins(int tx, int ty, int rx, int ry, DIR d, FurnisherItem item) {
         return item.sprite(rx, ry) != null && item.sprite(rx, ry).sData() >= this.sData();
      }

      @Override
      public boolean render(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade, boolean isCandle) {
         return false;
      }

      @Override
      public void renderBelow(SPRITE_RENDERER r, ShadowBatch s, int data, RenderData.RenderIterator it, double degrade) {
         if (this.sData() > 1) {
            super.render(r, s, 15, it, degrade, false);
         }

         super.render(r, s, data, it, degrade, false);
      }
   }
}

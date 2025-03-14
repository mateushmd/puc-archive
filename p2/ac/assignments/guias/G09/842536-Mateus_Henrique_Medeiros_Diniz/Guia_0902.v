/*
	Guia_0902.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`include "clock.v"

module Guia_0902;
	wire clock;
	
	clock clk ( clock );
	
	wire p1,p3,p6,p12;
	
	pulse1 pulse1 ( p1, clock );
	pulse3 pulse3 ( p3, clock );
	pulse6 pulse6 ( p6, clock );
	pulse12 pulse12 ( p12, clock );
	
	initial begin
		$dumpfile ( "Guia_0902.vcd" );
		$dumpvars ( 1, clock, p1, p3, p6, p12 );
		#376 $finish;
	end
endmodule

module pulse1 ( signal, clock );
	input clock;
	output signal;	
	reg signal;
	
	always @ ( clock )
	begin
		signal = 1'b1;
		#1 signal = 1'b0;
		#1 signal = 1'b1;
		#1 signal = 1'b0;
		#1 signal = 1'b1;
		#1 signal = 1'b0;
		#1 signal = 1'b1;
		#1 signal = 1'b0;
		#1 signal = 1'b1;
		#1 signal = 1'b0;
		#1 signal = 1'b1;
		#1 signal = 1'b0;
	end
endmodule

module pulse3 ( signal, clock );
	input clock;
	output signal;
	reg signal;
	
	always @ ( clock )
	begin
		signal = 1'b1;
		#3 signal = 1'b0;
		#3 signal = 1'b1;
		#3 signal = 1'b0;
	end
endmodule

module pulse6 ( signal, clock );
	input clock;
	output signal;
	reg signal;
	
	always @ ( clock )
	begin
		signal = 1'b1;
		#6 signal = 1'b0;
	end
endmodule

module pulse12 ( signal, clock );
	input clock;
	output signal;
	reg signal;
	
	always @ ( clock )
	begin
		signal = 1'b1;
		#12 signal = 1'b0;
	end
endmodule
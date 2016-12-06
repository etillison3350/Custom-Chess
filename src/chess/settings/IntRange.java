package chess.settings;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public final class IntRange {

	private final NavigableSet<Integer> changes = new TreeSet<>();

	public IntRange(final int lower, final int upper) {
		this(lower, upper, false);
	}

	public IntRange(final int lower, final int upper, final boolean not) {
		if (not) changes.add(Integer.MIN_VALUE);
		changes.add(lower);
		changes.add(upper);
	}

	public IntRange or(final IntRange range) {
		for (final Iterator<Integer> iter = range.changes.iterator(); iter.hasNext();) {
			final Integer lower = iter.next();
			final Integer upper = iter.hasNext() ? iter.next() : Integer.MAX_VALUE;

			changes.add(lower);
			changes.add(upper);
			final Set<Integer> toRemove = new HashSet<>();
			for (final Integer i : changes) {
				if (i > upper) break;
				if (i > lower) toRemove.add(i);
			}
			changes.removeAll(toRemove);
		}
		return this;
	}

	public IntRange and(final IntRange range) {
		final Set<Integer> result = new HashSet<>();

		changes.stream().filter(range::contains).forEach(result::add);
		range.changes.stream().filter(this::contains).forEach(result::add);

		changes.clear();
		changes.addAll(result);

		return this;
	}

	public IntRange xor(final IntRange range) {
		for (final int i : range.changes) {
			if (changes.contains(i))
				changes.remove(i);
			else
				changes.add(i);
		}
		return this;
	}

	public boolean contains(final int b) {
		boolean contains = false;
		for (final int change : changes) {
			if (change > b) break;
			contains = !contains;
		}
		return contains;
	}

}
